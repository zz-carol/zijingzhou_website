def CountWithPseudocounts(Motifs):
    r_num = len(Motifs)
    c_num = len(Motifs[0])
    count = {}
    for n in "ACGT":
        count[n] = []
        for j in range(c_num):
             count[n].append(1)
    for i in range(r_num):
        for j in range(c_num):
            symbol = Motifs[i][j]
            count[symbol][j] += 1
    return count

def ProfileWithPseudocounts(Motifs):
    t = len(Motifs)
    k = len(Motifs[0])
    profile = CountWithPseudocounts(Motifs)
    for i in profile:
        for j in range(k):
            profile[i][j] = profile[i][j] / (t + 4)
    return profile

def GreedyMotifSearchWithPseudocounts(Dna, k, t):
    BestMotifs = []
    for i in range(0, t):
        BestMotifs.append(Dna[i][0:k])
    n = len(Dna[0])
    for i in range(n-k+1):
        Motifs = []
        Motifs.append(Dna[0][i:i+k])
        for j in range(1, t):
            P = ProfileWithPseudocounts(Motifs[0:j])
            Motifs.append(ProfileMostProbableKmer(Dna[j], k, P))
        if Score(Motifs) < Score(BestMotifs):
            BestMotifs = Motifs
    return BestMotifs

def Consensus(Motifs):
    k = len(Motifs[0])
    count = CountWithPseudocounts(Motifs)
    consensus = ""
    for j in range(k):
        m = 0
        frequentSymbol = ""
        for symbol in "ACGT":
            if count[symbol][j] > m:
                m = count[symbol][j]
                frequentSymbol = symbol
        consensus += frequentSymbol
    return consensus

def Score(Motifs):
    result = 0
    motif = Consensus(Motifs)
    r_num = len(Motifs)
    c_num = len(Motifs[0])
    for j in range(c_num):
        n = motif[j]
        for i in range(r_num):
            if Motifs[i][j] != n:
                result += 1
    return result

def Pr(Text, Profile):
    result = 1.0
    for i in range(len(Text)):
        if Profile[Text[i]][i] != 0:
            result *= Profile[Text[i]][i]
        else:
            result = 0
    return result

def ProfileMostProbableKmer(Text, k, Profile):
    result = ""
    max = -1
    n = len(Text)
    for i in range(n - k + 1):
        Pattern = Text[i:i+k]
        prob = Pr(Pattern, Profile)
        if prob > max:
            max = prob
            result = Pattern
    return result

def Motifs(Profile, Dna):
    result = []
    k = len(Profile["A"])
    for i in range(len(Dna)):
        result.append(ProfileMostProbableKmer(Dna[i], k, Profile))
    return result

import random

def RandomMotifs(Dna, k, t):
    l = len(Dna[0])
    RandomMotif =[]
    for i in range(t):
        r = random.randint(1, l-k) 
        RandomMotif.append(Dna[i][r:r+k])
    return RandomMotif

def RandomizedMotifSearch(Dna, k, t):
    M = RandomMotifs(Dna, k, t)
    BestMotifs = M
    while True:
        Profile = ProfileWithPseudocounts(M)
        M = Motifs(Profile, Dna)
        if Score(M) < Score(BestMotifs):
            BestMotifs = M
        else:
            return BestMotifs 

def RepeatedRandomizedMotifSearch(Dna, k, t, N):
    BestMotifs = RandomizedMotifSearch(Dna, k, t)
    for i in range(N):
        M = RandomizedMotifSearch(Dna, k, t)
        if Score(M) < Score(BestMotifs):
            BestMotifs = M
    return BestMotifs

def Normalize(Probabilities):
    result = Probabilities
    s = sum(Probabilities.values())
    for i in Probabilities:
        result[i] = Probabilities.get(i) / s
    return result 

def WeightedDie(Probabilities):
    r_p = random.uniform(0, 1)
    p = 0
    for i in Probabilities:
        p += Probabilities[i]
        if r_p < p:
            return i

def ProfileGeneratedString(Text, profile, k):
    n = len(Text)
    probabilities = {} 
    for i in range(0, n-k+1):
        probabilities[Text[i:i+k]] = Pr(Text[i:i+k], profile)
    probabilities = Normalize(probabilities)
    return WeightedDie(probabilities)

def GibbsSampler(Dna, k, t, N):
    BestMotifs = []
    Motifs = RandomMotifs(Dna, k, t)
    BestMotifs = Motifs
    for j in range(1, N):
        i = random.randint(0, t-1)
        Profile = ProfileWithPseudocounts(Motifs)
        Motifs[i] = ProfileGeneratedString(Dna[i], Profile, k)
        if Score(Motifs) < Score(BestMotifs):
            BestMotifs = Motifs
    return BestMotifs

def RepeatedGibbsSampler(Dna, k, t, N, times):
    BestMotifs = GibbsSampler(Dna, k, t, N)
    for i in range(times):
        M = GibbsSampler(Dna, k, t, N)
        if Score(M) < Score(BestMotifs):
            BestMotifs = M
    return BestMotifs
