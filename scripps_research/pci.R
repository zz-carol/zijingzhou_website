pci_con<-read.csv("World_Country_PCI_2019.csv")

download.file(
  "https://thematicmapping.org/downloads/TM_WORLD_BORDERS-0.3.zip",
  "/tmp/countries.zip")

dir.create("/tmp/countries/", showWarnings = FALSE)
unzip("/tmp/countries.zip", exdir="/tmp/countries")
countries_shapes <- st_read("/tmp/countries/TM_WORLD_BORDERS-0.3.shp") %>% 
  filter(!ISO3 %in% c("ATF", "ATA"))

dat <- filter(pci_con, place_i == "ZAF")

dat_map <- 
  right_join(dat,
             countries_shapes,
             by=c("place_j"="ISO3")) %>% 
  st_as_sf()

x1 <- quantile(dat_map$sci, .2, na.rm=T)
x2 <- x1 * 2
x3 <- x1 * 3
x5 <- x1 * 5
x10 <- x1 * 10
x25 <- x1 * 25
x100 <- x1 * 100

dat_map <- dat_map %>% 
  mutate(pci_bkt = case_when(
    pci <= 10 ~ "0-10",
    pci <= 20 ~ "10-20",
    pci <= 30 ~ "20-30",
    pci <= 40 ~ "30-40",
    pci <= 50 ~ "40-50")) %>% 
  mutate(pci_bkt = factor(pci_bkt, levels=c("0-10", "10-20", "20-30", "30-40",
                                            "40-50")))

curr_region_outline <- dat_map %>% 
  filter(place_j == "ZAF")

ggplot(st_transform(dat_map,"+proj=robin +lon_0=0 +x_0=0 +y_0=0 +ellps=WGS84 +datum=WGS84 +units=m +no_defs")) +
  geom_sf(aes(fill = pci_bkt), colour="#ADADAD", size=0.1) +
  geom_sf(data=curr_region_outline, fill="#A00000", colour="#A00000", size=0.2) +
  labs(fill = "PCI") +
  theme_void() +
  scale_fill_brewer(palette = "GnBu", na.value="gray", drop=FALSE) +
  theme(legend.title = element_blank(), 
        legend.text  = element_text(size = 8),
        legend.key.size = unit(0.8, "lines"),
        legend.position = "bottom", legend.box = "horizontal") +
  guides(fill = guide_legend(nrow = 1, title.hjust = 0.5))
