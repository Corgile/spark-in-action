package net.jgp.books.spark.ch03.lab320_dataset_books_to_dataframe;

import net.jgp.books.spark.ch03.x.model.Book;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.*;

import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * This example will read a CSV file, ingest it in a dataframe, convert the
 * dataframe to a dataset, and vice versa.
 *
 * @author jgp
 */
public class CsvToDatasetBookToDataframeApp implements Serializable {
    private static final long serialVersionUID = -1L;

    /**
     * This is a mapper class that will convert a Row to an instance of Book.
     * You have full control over it - isn't it great that sometimes you have
     * control?
     *
     * @author jgp
     */
    static class BookMapper implements MapFunction<Row, Book>, Serializable {
        private static final long serialVersionUID = -2L;

        @Override
        public Book call(Row value) throws Exception {
            Book b = new Book();
            b.setId(value.getAs("id"));
            b.setAuthorId(value.getAs("authorId"));
            b.setLink(value.getAs("link"));
            b.setTitle(value.getAs("title"));
            // date case
            String dateAsString = value.getAs("releaseDate");
            //b.setReleaseDate(dateAsString);
            if (dateAsString != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yy");
                Date date = new Date(dateFormat.parse(dateAsString).getTime());
                b.setReleaseDate(date);
            }
            return b;
        }
    }

    /**
     * It starts here
     *
     * @param args
     */
    public static void main(String[] args) {
        CsvToDatasetBookToDataframeApp app = new CsvToDatasetBookToDataframeApp();
        app.start();
    }

    /**
     * All the work is done here.
     */
    private void start() {
        SparkSession spark = SparkSession.builder()
                .appName("CSV to dataframe to Dataset<Book> and back")
                // Needed by Spark v3.0.0 (Thanks @dapeng09)
                .config("spark.sql.legacy.timeParserPolicy", "LEGACY")
                .master("local")
                .getOrCreate();

        Dataset<Row> df = spark.read()
                .option("inferSchema", "true")
                .option("header", "true")
                .csv("data/books.csv")
                .cache();

        System.out.println("*** Books ingested in a dataframe");
        df.show(5);
        df.printSchema();


        Encoder<Book> encoder = Encoders.bean(Book.class);
        Dataset<Book> bookDs = df.map(new BookMapper(), encoder);

        System.out.println("*** Books are now in a dataset of books");
        bookDs.show(5, 17);
        bookDs.printSchema();
    }
}
