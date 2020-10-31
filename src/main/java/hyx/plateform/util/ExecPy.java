package hyx.plateform.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;

public class ExecPy {
    static final AtomicBoolean isRunning = new AtomicBoolean(false);
    static final Map<String, String> map = new ConcurrentHashMap<>();
    static final String scriptName = "\\proinstall.py";
//    "C://Users/PC/Desktop/djk/proinstall.py"
    public static ExecutorService executorService = Executors.newSingleThreadExecutor();

    public static List<String> getRunningInfo() {
        String str = map.getOrDefault("Running", null);
        if(str == null) return null;
        List<String> res = new LinkedList<>();
        String[] strArray = str.split("\\\\");
        res.add(strArray[strArray.length - 2]);
        res.add(strArray[strArray.length - 1]);
        return res;
    }

    public static boolean execPy(String dir) {
        if(!isRunning.compareAndSet(false, true)) return false;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    map.put("Running", dir);
                    String[] arguments = new String[] {"python", dir + scriptName};
                    Process process = Runtime.getRuntime().exec(arguments);
                    BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    int res = process.waitFor();
                    System.out.println(res);
                    map.remove("Running");
                    isRunning.getAndSet(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        executorService.execute(runnable);
        return true;
    }

}
