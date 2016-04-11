package com.galiamov.anagram;

import java.util.Arrays;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

public class AnagramApp {

    public static void main(String[] args) throws Exception {

        JavaSparkContext sparkContext = new JavaSparkContext(configure());

        sparkContext
            .textFile(args[0])
            .distinct()
            .mapToPair(AnagramApp::sig)
            .mapToPair(Tuple2::swap).sortByKey().mapToPair(Tuple2::swap)
            .sortByKey()
            .reduceByKey((s1, s2) -> s1 + " " + s2)
            .filter(v -> v._2.indexOf(" ") > 0)
            .foreach(v -> System.out.println(v._2));

        sparkContext.stop();
    }

    private static SparkConf configure() {
        SparkConf conf = new SparkConf().setAppName("anagramApp");
        conf.setMaster("local[*]");
        return conf;
    }

    private static Tuple2<String, String> sig(final String s) {
        return new Tuple2<>(signature(s), s);
    }

    private static String signature(final String s) {
        char[] c = s.toCharArray();
        Arrays.sort(c);
        return String.valueOf(c);
    }

}
