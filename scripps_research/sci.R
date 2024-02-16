sci_con<-read.csv("countries-countries.csv")
sci_con<-rename(sci_con,sci=scaled_sci)

library(dplyr)
library(tidyverse)
library(sf)

# Download country borders into a temp directory, unzip and use
download.file(
  "https://thematicmapping.org/downloads/TM_WORLD_BORDERS-0.3.zip",
  "/tmp/countries.zip")

dir.create("/tmp/countries/", showWarnings = FALSE)
unzip("/tmp/countries.zip", exdir="/tmp/countries")
countries_shapes <- st_read("/tmp/countries/TM_WORLD_BORDERS-0.3.shp") %>% 
  filter(!ISO3 %in% c("ATF", "ATA"))

# Make a vector of regions to generate maps for
regions <- c("IN","ZA","MW")

# Create the graph for each of the regions in the list of regions
dat <- filter(sci_con, user_loc == "ZA")
  
  dat_map <- 
    right_join(dat,
               countries_shapes,
               by=c("fr_loc"="ISO2")) %>% 
    st_as_sf
  
  x1 <- quantile(dat_map$sci, .2, na.rm=T)
  x2 <- x1 * 2
  x3 <- x1 * 3
  x5 <- x1 * 5
  x10 <- x1 * 10
  x25 <- x1 * 25
  x100 <- x1 * 100
  
  dat_map <- dat_map %>% 
    mutate(sci_bkt = case_when(
      sci < x1 ~ "< 1x (Country 20th percentile)",
      sci < x2 ~ "1-2x",
      sci < x3 ~ "2-3x",
      sci < x5 ~ "3-5x",
      sci < x10 ~ "5-10x",
      sci < x25 ~ "10-25x",
      sci < x100 ~ "25-100x",
      sci >= x100 ~ ">= 100x")) %>% 
    mutate(sci_bkt = factor(sci_bkt, levels=c("< 1x (Country 20th percentile)", "1-2x", "2-3x", "3-5x",
                                              "5-10x", "10-25x", "25-100x", ">= 100x")))
  
  curr_region_outline <- dat_map %>% 
    filter(fr_loc == "ZA")
  
  ggplot(st_transform(dat_map, "+proj=robin +lon_0=0 +x_0=0 +y_0=0 +ellps=WGS84 +datum=WGS84 +units=m +no_defs")) +
    geom_sf(aes(fill = sci_bkt), colour="#ADADAD", size=0.1) +
    geom_sf(data=curr_region_outline, fill="#A00000", colour="#A00000", size=0.2) +
    labs(fill = "SCI") +
    theme_void() +
    scale_fill_brewer(palette = "GnBu", na.value="gray", drop=FALSE) +
    theme(legend.title = element_blank(), 
          legend.text  = element_text(size = 8),
          legend.key.size = unit(0.8, "lines"),
          legend.position = "bottom", legend.box = "horizontal") +
    guides(fill = guide_legend(nrow = 1, title.hjust = 0.5))
