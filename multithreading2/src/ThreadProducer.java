import java.util.ArrayList;
import java.util.List;

public class ThreadProducer {

    static List<Integer> list = new ArrayList<Integer>();

    static class Producer implements Runnable {

        List<Integer> list1;

        public Producer(List<Integer> list) {
            this.list1 = list;
        }

        @Override
        public void run() {
            synchronized (list1) {
                for (int i = 0; i < 10; i++) {
                    if (list1.size() >=1) {
                        try {
                            System.out.println("producer is waiting ");
                            list1.wait();
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }

                    for(int j=0;j<10;j++)
                    {
                        list1.add(j);
                    }
                    System.out.println("Producer Produced"+list1);
//                    list1.add(i)
                    list1.notifyAll();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }

            }

            //To change body of generated methods, choose Tools | Templates.
        }

    }

    static class Consumer implements Runnable {

        List<Integer> list2;

        public Consumer(List<Integer> list) {
            this.list2 = list;
        }

        @Override
        public void run() {

            boolean flag=false;
            synchronized (list2) {
                for (int i = 0; i < 10; i++) {

                    if(list2.size()==0){
                        flag=true;
                    }
                    while (flag) {
                        flag=false;
                        System.out.println("Consumer is waiting");
                        try {
                            list2.wait();
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();;
                        }

                    }

//                    if(list2.size()==0)
//                    {
//                        list2.notifyAll();
//                    }
                    int k = list2.remove(0);
                    flag=true;
                    System.out.println("consume=" + k);
                    list2.notifyAll();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                }

            }
        }

    }

    public static void main(String[] args) {
        Thread producer = new Thread(new Producer(list));
        Thread consumer = new Thread(new Consumer(list));
        producer.start();
        consumer.start();



    }
}
