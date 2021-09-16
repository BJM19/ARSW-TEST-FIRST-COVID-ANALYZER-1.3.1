package eci.arsw.covidanalyzer.processedthread;

import eci.arsw.covidanalyzer.Result;
import eci.arsw.covidanalyzer.ResultAnalyzer;
import eci.arsw.covidanalyzer.TestReader;

import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ProcessedThread extends Thread{
    private List<File> files;
    private int rangoi;
    private int rangof;
    private TestReader testReader;
    private ResultAnalyzer resultAnalyzer;
    private AtomicInteger amountOfFilesProcessed;


    public ProcessedThread(String nombre, List<File> files, int rangoi, int rangof){
        super(nombre);
        this.files=files;
        this.rangoi=rangoi;
        this.rangof=rangof;
        resultAnalyzer = new ResultAnalyzer();
        testReader = new TestReader();
        amountOfFilesProcessed = new AtomicInteger();
    }

    public void run(){
        amountOfFilesProcessed.set(0);
        int i = rangoi;
        while (i<rangof) 
        {
        	List<Result> results = testReader.readResultsFromFile(files.get(i));
            for (Result result : results) {
                resultAnalyzer.addResult(result);
            }
            amountOfFilesProcessed.incrementAndGet();
            i++;
        }
       
    }
}
