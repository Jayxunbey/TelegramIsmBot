package org.telegram.buttonBot.bot.download.model;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Result {
   public String file_id;
   public String file_unique_id;
   public long file_size;
   public String file_path;

}
