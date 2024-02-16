confirm_spikein3.0.1 = function(spikein_dnanexus, spikein_ssss, spikein_sampleid) {
  
  library(tidyverse)
  
  batch = spikein_ssss$Batch
  sample_id = spikein_ssss$Sample.ID
  expected_spike_in = spikein_ssss$Expected.spike.in
  detected_spike_in_reads = spikein_ssss$expected_spike_in_reads
  
  # detected_spike_in, other_spike_in_detected
  for (i in colnames(spikein_dnanexus[2:ncol(spikein_dnanexus)])) {
    dsi = spikein_dnanexus %>%
      arrange(desc(spikein_dnanexus[i])) %>%
      select(seq_mapped_to, all_of(i)) %>%
      filter(!!sym(i) > 2)
    if (i == colnames(spikein_dnanexus[2])) {
      if (is.na(dsi[1, 2])) {
        detected_spike_in = "NA"
      } else {
        detected_spike_in = dsi[1, 1]
      }
      if (!(is.na(dsi[2, 1]))) {
        for (i in 2:nrow(dsi)) {
          if (i == 2) {
            other_spike_in_detected = paste0(str_extract(dsi[i, 1], '[1-9]+'), " ", "(", dsi[i, 2], ")")
          } else {
            other_spike_in_detected = paste0(other_spike_in_detected, ",", " ", str_extract(dsi[i, 1], '[1-9]+'), " ", "(", dsi[i, 2], ")")
          }
        }
      } else {
        other_spike_in_detected = "NA"
      }
    } else {
      if (is.na(dsi[1, 2])) {
        detected_spike_in = append(detected_spike_in, "NA")
      } else {
        detected_spike_in = append(detected_spike_in, dsi[1, 1])
      }
      if (!(is.na(dsi[2, 1]))) {
        for (i in 2:nrow(dsi)) {
          if (i == 2) {
            other_spike_in_detected2 = paste0(str_extract(dsi[i, 1], '[1-9]+'), " ", "(", dsi[i, 2], ")")
          } else {
            other_spike_in_detected2 = paste0(other_spike_in_detected2, ",", " ", str_extract(dsi[i, 1], '[1-9]+'), " ", "(", dsi[i, 2], ")")
          }
        }
      } else {
        other_spike_in_detected2 = "NA"
      }
      other_spike_in_detected = append(other_spike_in_detected, other_spike_in_detected2)
    }
  }
  
  # expected_spike_in_confirmed
  index = 1
  for (i in expected_spike_in) {
    if (index == 1) {
      if (i == detected_spike_in[index]) {
        expected_spike_in_confirmed = "Yes"
      } else {
        expected_spike_in_confirmed = "No"
      }
    } else {
      if (i == detected_spike_in[index]) {
        expected_spike_in_confirmed = append(expected_spike_in_confirmed, "Yes")
      } else {
        expected_spike_in_confirmed = append(expected_spike_in_confirmed, "No")
      }
    }
    index = index + 1
  }
  
  spikein_confirmed = data.frame(batch, sample_id, expected_spike_in, detected_spike_in, expected_spike_in_confirmed, other_spike_in_detected, detected_spike_in_reads)
  
  # merge spikein_ssss and spikein_confirmed
  colnames(spikein_ssss)[3] <- "sample_id"
  spikein_confirmed2 = inner_join(spikein_ssss, spikein_confirmed, by = "sample_id")
  
  # merge spikein_sampleid and spikein_confirmed2
  colnames(spikein_sampleid)[1] <- "sample_id"
  spikein_confirmed3 = left_join(spikein_sampleid, spikein_confirmed2, by = "sample_id")
  
  spikein_confirmed3 = spikein_confirmed3 %>% 
    select(-c(Expected.spike.in, batch))
  
  return(spikein_confirmed3)
  
}
