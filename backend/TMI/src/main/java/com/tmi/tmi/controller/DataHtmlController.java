package com.tmi.tmi.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
@CrossOrigin("*")
@RequestMapping("/jsoup")
public class DataHtmlController {
    @GetMapping
    public boolean parse(){
        try{
            File file = new File("./surefire-report.html");
            Document doc = Jsoup.parse(file, "UTF-8");
            Elements div = doc.select("div.section");
//            System.out.println(div);
            int index = 0;
            for(int i=0; i < div.size(); i++){
                if(div.get(i).select("h2").text().equals("Test Cases")){
                    index = i;
                    break;
                }
            }

            for(int i=index; i < div.size(); i++){
                //class
                if(div.get(i).select("h2").hasText()){
                    System.out.println(div.get(i).select("h2").text());
                }
                System.out.println(div.get(i).select("h3").text());
                Elements classes = div.get(i).select("td");
                for(int j=0; j < classes.size(); j++){
                    if(classes.get(j).select("td").hasText()){
                        System.out.println(classes.get(j).select("td").text());
                    }
                }
            }
        }
        catch (Exception e){
            System.out.println(e);
        }

        return true;
    }
}
