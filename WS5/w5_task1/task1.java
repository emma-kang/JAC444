
package w5_task1;
// Fork/Join Framework is used for parallel programming in Java
// a fork can viewed as an independent task that runs on a thread
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class task1 {
    /** Main **/
    public static void main(String[] args) {
        int size = 2000;
        double[][] a = new double[size][size];
        double[][] b = new double[size][size];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[i].length; j++) {
                a[i][j] = Math.random();
                b[i][j] = Math.random();
            }
        }
        /** Display output **/
        System.out.println("2000*2000 size");
        long time = System.currentTimeMillis();
        parallelAddMatrix(a, b);
        System.out.println("ParallelAddMatrix() " + (System.currentTimeMillis() - time)	+ " msec");
        time = System.currentTimeMillis();
        addMatrix(a, b);
        System.out.println("addMatrix() " + (System.currentTimeMillis() - time) + " msec");
    }

    public static double[][] parallelAddMatrix(double[][] a, double[][] b) {
        double[][] c = new double[a.length][a.length];

        RecursiveAction mainTask = new AddMatrix(a, b, c, 0, a.length, 0, a.length);
        // create ForkJoinPool with all available processors
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(mainTask);
        return c;
    }

    // RecursiveAction is for a task that doesn't return a value /  subclasses of ForkJoinTask
    private static class AddMatrix extends RecursiveAction {
        private static final long serialVersionUID = 1L;
        private final static int THRESHOLD = 100;
        private double[][] a;
        private double[][] b;
        private double[][] c;
        private int x1;
        private int x2;
        private int y1;
        private int y2;

        public AddMatrix(double[][] a, double[][] b, double[][] c, int x1, int x2, int y1, int y2) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.x1 = x1;
            this.x2 = x2;
            this.y1 = y1;
            this.y2 = y2;
        }

        // override the compute() method to specify how a task is performed
        @Override
        protected void compute() {
            // perform the task
            if (((x2 - x1) < THRESHOLD) || ((y2 - y1) < THRESHOLD)) {
                for (int i = x1; i < x2; i++) {
                    for (int j = y1; j < y2; j++) {
                        c[i][j] = a[i][j] + b[i][j];
                    }
                }
            } else {
                int midX = (x1 + x2) / 2;
                int midY = (y1 + y2) / 2;

                invokeAll(
                        new AddMatrix(a, b, c, x1, midX, y1, midY),
                        new AddMatrix(a, b, c, midX, x2, y1, midY),
                        new AddMatrix(a, b, c, x1, midX, midY, y2),
                        new AddMatrix(a, b, c, midX, x2, midY, y2));
            }
        }
    }

    public static double[][] addMatrix(double[][] a, double[][] b) {
        double[][] result = new double[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                result[i][j] = a[i][j] + b[i][j];
            }
        }
        return result;
    }
}
