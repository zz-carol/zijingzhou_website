library(outbreakinfo)
library(dplyr)
library(knitr)
library(lubridate)
library(ggplot2)
library(RColorBrewer)
library(tidyverse)

authenticateUser()

###South Africa
##Cases & Death
blr_52_date=seq(as.Date("2021-05-01"), as.Date("2021-12-31"), by="days")

blr_52_date2=as.character(blr_52_date)

sa_52_cases=getEpiData(name = "South Africa", date=blr_52_date2)

blr_52_date3=seq(as.Date("2022-01-01"), as.Date("2022-07-31"), by="days")

blr_52_date4=as.character(blr_52_date3)

sa_52_cases2=getEpiData(name = "South Africa", date=blr_52_date4)

sa_52_cases3<-rbind(sa_52_cases, sa_52_cases2)

#Daily New Cases
ggplot(sa_52_cases3, aes(date, confirmed_numIncrease, fill = name, group = name)) +
  geom_bar(stat = "identity") +
  theme_minimal() +
  theme(legend.position = "none") +
  facet_wrap(~name )+
  scale_x_date(date_labels = "%b %Y",expand = c(0,0))+
  scale_y_continuous(labels = scales::comma) +
  theme(text = element_text(size = 16),
        legend.title = element_blank(),
        axis.title = element_blank()) +
  labs(title="Daily new COVID-19 cases")+
  guides(colour = guide_legend(nrow = nrow))

#Cases per 100k
ggplot(sa_52_cases3, aes(date, confirmed_numIncrease_per_100k, fill = name, group = name)) +
  geom_bar(stat = "identity") +
  theme_minimal() +
  theme(legend.position = "none") +
  facet_wrap(~name )+
  scale_x_date(date_labels = "%b %Y",expand = c(0,0))+
  scale_y_continuous(labels = scales::comma) +
  theme(text = element_text(size = 16),
        legend.title = element_blank(),
        axis.title = element_blank()) +
  labs(title="Daily new COVID-19 cases per 100,000 residents")+
  guides(colour = guide_legend(nrow = nrow))

#Cases Rolling
ggplot(sa_52_cases3, aes(date, confirmed_rolling, fill = name, group = name)) +
  geom_bar(stat = "identity") +
  theme_minimal() +
  theme(legend.position = "none") +
  facet_wrap(~name )+
  scale_x_date(date_labels = "%b %Y",expand = c(0,0))+
  scale_y_continuous(labels = scales::comma) +
  theme(text = element_text(size = 16),
        legend.title = element_blank(),
        axis.title = element_blank()) +
  labs(title="Daily new COVID-19 cases",
       subtitle = "7 day rolling average")+
  guides(colour = guide_legend(nrow = nrow))

library(tidyr)
sa_52_cases4 <- gather(sa_52_cases3, key = measure, value = Cases, 
             c("confirmed_rolling", "dead_rolling"))

sa_date<-as.Date("2021-11-15")

ggplot(sa_52_cases4, aes(x=date, y = Cases, group = measure, fill = measure)) + 
  geom_bar(stat = "identity")+
  labs(caption = "This data was obtained from JHU CSSE COVID-19 Data via the outbreak.info API")+
  scale_x_date(date_labels = "%b %Y",expand = c(0,0))+
  scale_y_continuous(labels = scales::comma)+
  scale_fill_discrete(labels = c("Infection", "Death"))+
  theme(legend.position = "bottom",
        axis.title.x = element_blank(),
        legend.title = element_blank(),
        panel.background = element_rect(fill = NA),
        panel.grid.major = element_line(colour = "#CCCCCC"),
        panel.grid.minor = element_line(colour = "#CCCCCC"))+
  geom_vline(xintercept = as.numeric(sa_date), linetype="dashed", 
             color = "black", size=1)+
  geom_text(x=as.numeric(sa_date2), y=22500, label="Omicron first detected")+
  geom_segment(aes(x =sa_date4 , y = 21250, xend = sa_date3, yend = 20000),
               arrow = arrow(length = unit(0.3, "cm")))

sa_date2<-as.Date("2021-09-15")
sa_date4<-as.Date("2021-10-15")
sa_date3<-as.Date("2021-11-10")

#Cases per 100k Rolling
ggplot(sa_52_cases3, aes(date, confirmed_rolling_per_100k, fill = name, group = name)) +
  geom_bar(stat = "identity") +
  theme_minimal() +
  theme(legend.position = "none") +
  facet_wrap(~name )+
  scale_x_date(date_labels = "%b %Y",expand = c(0,0))+
  scale_y_continuous(labels = scales::comma) +
  theme(text = element_text(size = 16),
        legend.title = element_blank(),
        axis.title = element_blank()) +
  labs(title="Daily new COVID-19 cases per 100,000 residents",
       subtitle = "7 day rolling average")+
  guides(colour = guide_legend(nrow = nrow))

#Death
sa_52_cases3$dead_numIncrease[sa_52_cases3$dead_numIncrease==-228]<-228

sa_52_cases3$dead_numIncrease_per_100k2<-abs(sa_52_cases3$dead_numIncrease_per_100k)

sa_52_cases3%>%
  filter(date=="2022-01-25")%>%
  select(date,dead_numIncrease,dead_numIncrease_per_100k2)

ggplot(sa_52_cases3, aes(date, dead_numIncrease, fill = name, group = name)) +
  geom_bar(stat = "identity") +
  theme_minimal() +
  theme(legend.position = "none") +
  facet_wrap(~name )+
  scale_x_date(date_labels = "%b %Y",expand = c(0,0))+
  scale_y_continuous(labels = scales::comma) +
  theme(text = element_text(size = 16),
        legend.title = element_blank(),
        axis.title = element_blank()) +
  labs(title="Daily new COVID-19 deaths")+
  guides(colour = guide_legend(nrow = nrow))

#Death per 100k
ggplot(sa_52_cases3, aes(date, dead_numIncrease_per_100k2, fill = name, group = name)) +
  geom_bar(stat = "identity") +
  theme_minimal() +
  theme(legend.position = "none") +
  facet_wrap(~name )+
  scale_x_date(date_labels = "%b %Y",expand = c(0,0))+
  scale_y_continuous(labels = scales::comma) +
  theme(text = element_text(size = 16),
        legend.title = element_blank(),
        axis.title = element_blank()) +
  labs(title="Daily new COVID-19 deaths per 100,000 residents")+
  guides(colour = guide_legend(nrow = nrow))

#Death per 100k Rolling
ggplot(sa_52_cases3, aes(date, dead_rolling, fill = name, group = name)) +
  geom_bar(stat = "identity") +
  theme_minimal() +
  theme(legend.position = "none") +
  facet_wrap(~name )+
  scale_x_date(date_labels = "%b %Y",expand = c(0,0))+
  scale_y_continuous(labels = scales::comma) +
  theme(text = element_text(size = 16),
        legend.title = element_blank(),
        axis.title = element_blank()) +
  labs(title="Daily new COVID-19 deaths",
       subtitle = "7 day rolling average")+
  guides(colour = guide_legend(nrow = nrow))

ggplot(sa_52_cases3, aes(date, dead_rolling, fill = name, group = name)) +
  geom_bar(stat = "identity") +
  theme_minimal() +
  theme(legend.position = "none") +
  scale_x_date(date_labels = "%b %Y",expand = c(0,0))+
  scale_y_continuous(labels = scales::comma) +
  theme(text = element_text(size = 16),
        legend.title = element_blank(),
        axis.title.x = element_blank()) +
  ylab("Cases")+
  guides(colour = guide_legend(nrow = nrow))

ggplot(sa_52_cases3, aes(date, dead_rolling_per_100k, fill = name, group = name)) +
  geom_bar(stat = "identity") +
  theme_minimal() +
  theme(legend.position = "none") +
  facet_wrap(~name )+
  scale_x_date(date_labels = "%b %Y",expand = c(0,0))+
  scale_y_continuous(labels = scales::comma) +
  theme(text = element_text(size = 16),
        legend.title = element_blank(),
        axis.title = element_blank()) +
  labs(title="Daily new COVID-19 deaths per 100,000 residents",
       subtitle = "7 day rolling average")+
  guides(colour = guide_legend(nrow = nrow))

#---------------------------------------------------------------------------------------

##Lineages
sa_365d=getAllLineagesByLocation(location = "South Africa",ndays = 365)

sa_365d=getAllLineagesByLocation(location = "South Africa",ndays = 456)

sa_52=sa_365d%>%
  filter(date>="2021-05-01"&date<="2022-07-31")

sa_52$lineage2[sa_52$lineage %in% c('ay.116','ay.120.2','ay.122','ay.19','ay.32','ay.37',
                                    'ay.38','ay.43','ay.45','ay.46','ay.6','ay.75','ay.91',
                                    'ay.99','b.1.617.2')]<-"Delta"

sa_52_delta1<-sa_52%>%
  filter(lineage2=="Delta")

sa_52_delta2<-sa_52_delta1%>%
  group_by(date)%>%
  summarise(prevalence_rolling2=sum(prevalence_rolling))%>%
  mutate(lineage2="Delta",prevalence_rolling=prevalence_rolling2)%>%
  select(date,prevalence_rolling,lineage2)

sa_other<-sa_52%>%
  filter(lineage2=="other")

sa_other$lineage[sa_other$lineage=="other"]<-"Other"

sa_other1<-sa_other%>%
  mutate(lineage2=lineage)%>%
  select(date,prevalence_rolling,lineage2)

sa_52$lineage2[sa_52$lineage %in% c('b.1.351')]<-"B.1.351"

sa_52_b1351<-sa_52%>%
  filter(lineage2=="B.1.351")%>%
  select(date,prevalence_rolling,lineage2)

sa_52$lineage2[sa_52$lineage %in% c('ba.1','ba.1.1','ba.1.17','ba.1.17.2','ba.1.18','ba.1.21')]<-"BA.1"

sa_52_ba1_1<-sa_52%>%
  filter(lineage2=="BA.1")

sa_52_ba1<-sa_52_ba1_1%>%
  group_by(date)%>%
  summarise(prevalence_rolling2=sum(prevalence_rolling))%>%
  mutate(lineage2="BA.1",prevalence_rolling=prevalence_rolling2)%>%
  select(date,prevalence_rolling,lineage2)

sa_52$lineage2[sa_52$lineage %in% c('ba.2','ba.2.15')]<-"BA.2"

sa_52_ba2_1<-sa_52%>%
  filter(lineage2=="BA.2")

sa_52_ba2<-sa_52_ba2_1%>%
  group_by(date)%>%
  summarise(prevalence_rolling2=sum(prevalence_rolling))%>%
  mutate(lineage2="BA.2",prevalence_rolling=prevalence_rolling2)%>%
  select(date,prevalence_rolling,lineage2)

sa_52$lineage2[sa_52$lineage %in% c('ba.4','ba.4.1','ba.4.1.1','ba.4.7')]<-"BA.4"

sa_52_ba4_1<-sa_52%>%
  filter(lineage2=="BA.4")

sa_52_ba4<-sa_52_ba4_1%>%
  group_by(date)%>%
  summarise(prevalence_rolling2=sum(prevalence_rolling))%>%
  mutate(lineage2="BA.4",prevalence_rolling=prevalence_rolling2)%>%
  select(date,prevalence_rolling,lineage2)

sa_52$lineage2[sa_52$lineage %in% c('ba.5','ba.5.1','ba.5.2','ba.5.2.1','ba.5.3.1')]<-"BA.5"

sa_52_ba5_1<-sa_52%>%
  filter(lineage2=="BA.5")

sa_52_ba5<-sa_52_ba5_1%>%
  group_by(date)%>%
  summarise(prevalence_rolling2=sum(prevalence_rolling))%>%
  mutate(lineage2="BA.5",prevalence_rolling=prevalence_rolling2)%>%
  select(date,prevalence_rolling,lineage2)

sa_52$lineage2[sa_52$lineage %in% c('be.1')]<-"BE.1"

sa_52_be1<-sa_52%>%
  filter(lineage2=="BE.1")%>%
  select(date,prevalence_rolling,lineage2)

sa_52$lineage2[sa_52$lineage %in% c('c.1.2')]<-"C.1.2"

sa_52_c12<-sa_52%>%
  filter(lineage2=="C.1.2")%>%
  select(date,prevalence_rolling,lineage2)

sa_52_p1<-rbind(sa_52_b1351,sa_52_ba1)

sa_52%>%
  group_by(lineage)%>%
  summarise(min_prevalence=min(prevalence_rolling),
            max_prevalence=max(prevalence_rolling))%>%
  arrange(desc(max_prevalence))%>%
  kable()

sa_52$lineage2<-sa_52$lineage

sa_52$lineage2[sa_52$lineage=='b.1.351']<-"Beta"

sa_52$lineage2[sa_52$lineage %in% 
                 c('ba.1.18','c.1.2','ba.1.21','ba.2.15','ba.4.1.1')]<-"other"

colourCount = length(unique(sa_52_p$lineage2))
getPalette = colorRampPalette(brewer.pal(9, "Set3"))

ggplot(sa_52_pr,aes(x=date,y=prevalence_rolling,group=lineage2,fill=lineage2))+
  geom_area(color="black")+
  scale_x_date(date_labels = "%b %Y",expand = c(0,0))+
  scale_y_continuous(labels = scales::percent)+
  scale_fill_manual(values = getPalette(colourCount),name = "Lineage")+
  labs(y="Proportion")+
  theme_minimal()+
  theme(legend.position = "bottom",legend.background = element_rect(fill = "#eeeeec",
                                                                    color = NA),
        axis.ticks = element_line(size = 0.5, colour = "#555555"), 
        axis.ticks.length = unit(5, "points"),
        panel.grid = element_blank(),
        axis.title.x = element_blank()) +
  theme(legend.position = "bottom",plot.caption = element_text(size = 18))


sa_other1$prevalence_rolling[sa_other1$prevalence_rolling=="1"]<-"0.3"

sa_52_p$prevalence_rolling[sa_52_p$prevalence_rolling=="0.85"]<-"0.3023256"

sa_52_p$prevalence_rolling<-as.numeric(sa_52_p$prevalence_rolling)

sa_52_p%>%
  filter(date>="2022-07-01")%>%
  group_by(date)%>%
  summarise(prevalence_rolling2=sum(prevalence_rolling))%>%
  arrange(date)

write.csv(sa_52_p,"/Users/zijingzhou/Desktop/sa_52_prevalence.csv", row.names = FALSE)

sa_52_pr<-read.csv("sa_52_prevalence.csv")

sa_52_pr$date<-as.Date(sa_52_pr$date)

#---------------------------------------------------------------------------------------

##Mutations
sa_lineages<-sa_52%>%
  filter(!(lineage2 %in% c('other','Delta','Beta')))%>%
  pull(lineage2)%>%
  unique()

sa_52_mutations=getMutationsByLineage(pangolin_lineage=sa_lineages)

plotMutationHeatmap2(sa_52_mutations,lightBorders = FALSE)

plotMutationHeatmap2 = function(df, gene2Plot = "S", title = NULL, lightBorders = TRUE) {
  MUTATIONPALETTE = c('#fff7f3','#fde0dd','#fcc5c0','#fa9fb5','#f768a1','#dd3497','#ae017e','#7a0177','#49006a')
  borderColour = ifelse(lightBorders, "#FFFFFF", "#555555")
  
  if(!is.null(df) && nrow(df) != 0){
    df = df %>% filter(gene == gene2Plot)
  }
  
  if(!is.null(df) && nrow(df) != 0){
    df = df %>%
      rowwise() %>%
      mutate(mutation_simplified = toupper(str_split(mutation, ":")[[1]][2])) %>%
      arrange(codon_num)
    
    mutation_simplified = df %>% pull(mutation_simplified) %>% unique()
    lineage = df %>% pull(lineage) %>% unique()
    blank = crossing(lineage, mutation_simplified)
    
    blank$mutation_simplified = factor(blank$mutation_simplified, levels = mutation_simplified)
    df$mutation_simplified = factor(df$mutation_simplified, levels = mutation_simplified)
    
    p = ggplot(df, aes(x = mutation_simplified, y = lineage, fill = prevalence)) +
      geom_tile(colour = borderColour, fill = "#dedede", data = blank) +
      geom_tile(colour = borderColour) +
      theme_minimal() +
      coord_fixed() +
      xlab("mutation") +
      scale_fill_gradientn(colours = MUTATIONPALETTE, limits = c(0,1), 
                           labels = scales::percent,name = "Prevalence") +
      labs(y="Lineage") +
      theme(axis.text.x = element_text(angle = 45, vjust = 0.9, hjust=1),
            axis.title.x = element_blank(),
            panel.grid = element_blank(),
            legend.position = "bottom",
            plot.caption = element_text(size = 18)
      )
    
    if(!is.null(title)) {
      p = p + ggtitle(title)
    }
    return(p)
  } else {
    warning("No data found. Check if there are mutations present in the gene you specified.")
  }
}

#---------------------------------------------------------------------------------------

##Lineage Comparison
ba1_lineages_string = lookupSublineages("ba.1", returnQueryString = TRUE)
ba1_label = c("BA.1")
names(ba1_label) = ba1_lineages_string

ba2_lineages_string = lookupSublineages("ba.2", returnQueryString = TRUE)
ba2_label = c("BA.2")
names(ba2_label) = ba2_lineages_string

ba4_lineages_string = lookupSublineages("ba.4", returnQueryString = TRUE)
ba4_label = c("BA.4")
names(ba4_label) = ba4_lineages_string

ba5_lineages_string = lookupSublineages("ba.5", returnQueryString = TRUE)
ba5_label = c("BA.5")
names(ba5_label) = ba5_lineages_string

delta_lineages_string = lookupSublineages("Delta", returnQueryString = TRUE)
delta_label = c("Delta")
names(delta_label) = delta_lineages_string

sa_lincomp1 = getPrevalence(pangolin_lineage = "b.1.351", location = "South Africa")

sa_lincomp1$lineage[sa_lincomp1$lineage=="b.1.351"]<-"B.1.351"

sa_ba1 = getPrevalence(pangolin_lineage = ba1_lineages_string,
                         location = "South Africa")

sa_ba2 = getPrevalence(pangolin_lineage = ba2_lineages_string,
                       location = "South Africa")

sa_ba4 = getPrevalence(pangolin_lineage = ba4_lineages_string,
                       location = "South Africa")

sa_ba5 = getPrevalence(pangolin_lineage = ba5_lineages_string,
                       location = "South Africa")

sa_delta = getPrevalence(pangolin_lineage = delta_lineages_string,
                         location = "South Africa")

sa_lincomp2 = dplyr::bind_rows(sa_lincomp1, sa_ba1, sa_ba2, sa_ba3, sa_ba4, sa_ba5, sa_delta)

sa_lincomp=sa_lincomp2%>%
  filter(date>="2021-05-01"&date<="2022-07-31")

plotPrevalenceOverTime2(sa_lincomp, labelDictionary = c(delta_label,ba1_label,ba2_label,
                                                       ba3_label,ba4_label,ba5_label))

sa_lincomp3%>%
  filter(lineage==ba5_lineages_string)%>%
  head(30,)

COLORPALETTE= c("#bab0ab", "#4E79A7", "#f28e2b", "#59a14f","#e15759", "#499894","#B6992D",  "#D37295", "#B07AA1","#9D7660", "#bcbd22",
                  "#aecBe8", "#FFBE7D",  "#8CD17D", "#FF9D9A",  "#86BCB6", "#F1CE63","#FABFD2",  "#D4A6C8", "#D7B5A6",  "#79706E")

plotPrevalenceOverTime2 <- function(df, colorVar = "lineage", labelDictionary = NULL) {
  if(!is.null(df) && nrow(df) > 0){
    if(!is.null(labelDictionary)) {
      df = df %>%
        mutate(lineage = ifelse(is.na(unname(labelDictionary[lineage])), lineage, 
                                unname(labelDictionary[lineage])))
    }
    
    p <- ggplot(df, aes(x = date, y = proportion, colour = .data[[colorVar]], 
                        fill = .data[[colorVar]], group = .data[[colorVar]])) +
      geom_ribbon(aes(ymin = proportion_ci_lower, ymax = proportion_ci_upper), 
                  alpha = 0.35, size = 0) +
      geom_line(size = 1) +
      labs(y="Porportion",caption = "This data was obtained from GISAID via the outbreak.info API")+
      scale_x_date(date_labels = "%b %Y", expand = c(0,0)) +
      scale_y_continuous(labels = scales::percent, expand = c(0,0)) +
      scale_colour_manual(values = COLORPALETTE[-1],name = "Lineage") +
      scale_fill_manual(values = COLORPALETTE[-1],name = "Lineage") +
      theme_minimal() +
      theme(legend.position = "bottom", axis.title.x = element_blank(), 
            plot.caption = element_text(size = 18))+
      guides(colour = guide_legend(nrow = 1))
    
    return(p)
    
  } else {
    warning("Dataframe is empty.")
  }
}

#Omicron
ba3_lineages_string = lookupSublineages("ba.3", returnQueryString = TRUE)
ba3_label = c("BA.3")
names(ba3_label) = ba3_lineages_string

sa_ba3 = getPrevalence(pangolin_lineage = ba3_lineages_string,
                       location = "South Africa")

sa_lincomp3 = dplyr::bind_rows(sa_ba1, sa_ba2, sa_ba3, sa_ba4, sa_ba5)

p<-plotPrevalenceOverTime2(sa_lincomp3, labelDictionary = c(ba3_label,ba1_label,ba2_label,
                                                        ba4_label,ba5_label))

p+expand_limits(x=as.Date("2021-05-01"))

plotCountOverTime <- function(df, colorVar = "lineage", labelDictionary = NULL) {
  if(!is.null(df) && nrow(df) > 0){
    if(!is.null(labelDictionary)) {
      df = df %>%
        mutate(lineage = ifelse(is.na(unname(labelDictionary[lineage])), lineage, 
                                unname(labelDictionary[lineage])))
    }
    
    p <- ggplot(df, aes(x = date, y = lineage_count_rolling, colour = .data[[colorVar]], 
                        fill = .data[[colorVar]], group = .data[[colorVar]])) +
      geom_line(size = 1) +
      labs(y="Count")+
      scale_x_date(date_labels = "%b %Y", expand = c(0,0)) +
      scale_y_continuous(labels = scales::comma, expand = c(0,0)) +
      scale_colour_manual(values = COLORPALETTE[-1],name = "Lineage") +
      scale_fill_manual(values = COLORPALETTE[-1],name = "Lineage") +
      theme_minimal() +
      theme(axis.title.x = element_blank())+
      guides(colour = guide_legend(ncol = 1))
    
    return(p)
    
  } else {
    warning("Dataframe is empty.")
  }
}

p<-plotCountOverTime(sa_lincomp3, labelDictionary = c(ba3_label,ba1_label,ba2_label,
                                                            ba4_label,ba5_label))
p
p+expand_limits(x=as.Date("2021-05-01"))+
  labs(caption = "This data was obtained from GISAID via the outbreak.info API")

sa_lincomp3%>%
  arrange(date)%>%
  head()

#---------------------------------------------------------------------------------------

##Prevalence Map

omicron_lineages_string = lookupSublineages("Omicron", returnQueryString = TRUE)

omicron_sa = getCumulativeBySubadmin(pangolin_lineage = omicron_lineages_string,
                                        location = "South Africa",ndays = 461)

library(sf)

gadm_sa_1 = st_read("gadm41_ZAF_1.shp")
gadm_world = st_read("gadm404.shp")

gadm_omicron_sa = left_join(gadm_sa_1, omicron_sa, by=c("NAME_1" = "name"))

gadm_omicron_sa2 <- gadm_omicron_sa %>% 
  mutate(p2 = case_when(
    proportion < 0.01 ~ "0-1",
    proportion < 0.05 ~ "1-5",
    proportion < 0.10 ~ "5-10",
    proportion < 0.20 ~ "10-20",
    proportion < 0.35 ~ "20-35",
    proportion < 0.50 ~ "35-50",
    proportion < 0.75 ~ "50-75",
    proportion < 1 ~ "75-100",)) %>% 
  mutate(p2 = factor(p2, levels=c("0-1", "1-5", "5-10", "10-20",
                                            "20-35","35-50","50-75","75-100")))


plotChoropleth2(gadm_omicron_sa2)+
  geom_text(data=gadm_omicron_sa2,aes(label = NAME_1), size = 3, hjust = 0.5)

plotChoropleth2 = function(df, fillVar = "proportion", title = NULL, subtitle = NULL, proj4 = "+proj=wag7 +lon_0=11 +datum=WGS84 +units=m +no_defs") {
  if(!is.null(df) && nrow(df) > 1){
    
    df_projected = st_transform(df, crs = proj4)
    
    p = ggplot(df_projected, aes_string(fill = fillVar)) +
      geom_sf(size = 0.1, colour = "#555555") +
      scale_fill_gradientn(colours = CHOROPLETH_PALETTE,
                        na.value = "#babab0", 
                        labels = scales::percent, limits = c(0,1),
                        name = "Prevalence") +
      labs(caption = "This data was obtained from GISAID via the outbreak.info API")+
      theme_void() +
      ggtitle(title, subtitle = subtitle)
    
    return(p)
  } else {
    warning("No data supplied to the plotting function.")
  }
}

CHOROPLETH_PALETTE = c('#ffffd9','#edf8b1','#c7e9b4','#7fcdbb','#41b6c4','#1d91c0','#225ea8','#253494','#081d58')

#---------------------------------------------------------------------------------------

##SCI



#---------------------------------------------------------------------------------------

##PCI

