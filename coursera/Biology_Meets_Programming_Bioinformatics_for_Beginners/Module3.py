def Count(Motifs):
    count = {}
    c_num = len(Motifs[0])
    for n in "ACGT":
        count[n] = []
        for j in range(c_num):
             count[n].append(0)
    r_num = len(Motifs)
    for i in range(r_num):
        for j in range(c_num):
            symbol = Motifs[i][j]
            count[symbol][j] += 1
    return count

def Profile(Motifs):
    t = len(Motifs)
    k = len(Motifs[0])
    profile = Count(Motifs)
    for i in profile:
        for j in range(k):
            profile[i][j] = profile[i][j] / t
    return profile

def Consensus(Motifs):
    k = len(Motifs[0])
    count = Count(Motifs)
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

def GreedyMotifSearch(Dna, k, t):
    BestMotifs = []
    for i in range(0, t):
        BestMotifs.append(Dna[i][0:k])
    n = len(Dna[0])
    for i in range(n-k+1):
        Motifs = []
        Motifs.append(Dna[0][i:i+k])
        for j in range(1, t):
            P = Profile(Motifs[0:j])
            Motifs.append(ProfileMostProbableKmer(Dna[j], k, P))
        if Score(Motifs) < Score(BestMotifs):
            BestMotifs = Motifs
    return BestMotifs
