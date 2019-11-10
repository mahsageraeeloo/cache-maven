package ir.mahsa.snippets.data;

public class SampleDataProvider {
    /*
    As you see there are two ways to make SampleDataProvider a Cacheable one
    1. implementing Cacheable Interface
    2.Anonymous class and calling readData
     */
    public String readData(Long key) {
        /* Read data from DB*/
        System.out.println("Reading " + key + "from DP ... ");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "DATA" + key;
    }
}
