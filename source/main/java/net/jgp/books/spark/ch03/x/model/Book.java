package net.jgp.books.spark.ch03.x.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private Integer id;
    private Integer authorId;
    private String title;
    private String link;
    private Date releaseDate;
}