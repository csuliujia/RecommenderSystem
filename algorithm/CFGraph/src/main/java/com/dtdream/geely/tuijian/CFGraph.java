package com.dtdream.geely.tuijian;

import com.aliyun.odps.data.TableInfo;
import com.aliyun.odps.graph.GraphJob;

import java.io.IOException;

/**
 * 一个超步就行了，类似于构造一个捯排表
 */

public class CFGraph {
    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Usage: <input> <output> [<movielimit> <personlimit> <cpu> <worker> <memory>]");
            System.exit(-1);
        }

        GraphJob job = new GraphJob();
        job.setGraphLoaderClass(CFLoader.class);
        job.setVertexClass(CFVertex.class);
        job.setLoadingVertexResolver(CFResolver.class);
        //job.setWorkerCPU();
        //job.setNumWorkers();
        //job.setWorkerMemory();

        //job.set(START_VERTEX, args[0]);
        job.addInput(TableInfo.builder().tableName(args[0]).build());
        job.addOutput(TableInfo.builder().tableName(args[1]).build());
        job.setMaxIteration(3);
        if (args.length >= 3) {
            job.setWorkerMemory(Integer.parseInt(args[2]));
        }
        if (args.length >= 4) {
            job.setNumWorkers(Integer.parseInt(args[3]));
        }
        if (args.length >= 5) {
            job.setWorkerCPU(Integer.parseInt(args[4]));
        }

        long startTime = System.currentTimeMillis();
        job.run();
        long runTime = System.currentTimeMillis() - startTime;
        System.out.println("Job Finished in " + runTime / 3600000 + " hours, "
                + (runTime % 3600000) / 60000 + " minutes, " + (runTime % 60000) / 1000 + " seconds");
    }
}