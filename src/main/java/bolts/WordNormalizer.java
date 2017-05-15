package bolts;

import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class WordNormalizer extends BaseBasicBolt {

    private List<String> results;
    private FileReader fileReader;

    public void prepare(Map stormConf, TopologyContext context) {
        // 获取StreamSataReaderSpout 传过来的每一行数据的属性信息 如 user_id,sku_id,cate
        Map<String, Map<String, List<String>>> inputFields = context.getThisInputFields();
        Iterator<String> iter = inputFields.keySet().iterator();
        while (iter.hasNext()){
            String key = iter.next();
            Map<String,List<String >> val = inputFields.get(key);
            Iterator<String> iter2 = val.keySet().iterator();
            while (iter2.hasNext()){
               List<String> valList = val.get(iter2.next());
               for (String item:valList){
                   System.out.println(item);
               }
            }

        }

        results = new ArrayList<String>();
//        String sql = null;
//        try {
//            this.fileReader = new FileReader(stormConf.get("table_struct").toString());
//            //Open the reader
//            BufferedReader reader = new BufferedReader(fileReader);
//            sql = reader.readLine();
//            System.out.println(sql);
//
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException("Error reading file [" + stormConf.get("table_struct") + "]");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (sql != null){
//            String[] sqlItems=sql.split("where");
//
//        }
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return super.getComponentConfiguration();
    }

    public void cleanup() {
        System.out.println("打印结果");
        for (String item : results) {
            System.out.println(item);
        }
    }

    /**
     * The bolt will receive the line from the
     * words file and process it to Normalize this line
     * <p>
     * The normalize will be put the words in lower case
     * and split the line to get all words in this
     */
    public void execute(Tuple input, BasicOutputCollector collector) {
        String sentence = input.getString(0);
        String[] words = sentence.split("\\|");
        //选择商品类型为8的数据
        if (Integer.valueOf(words[3]) == 8) {
            String ansStr = "";
            for (int i = 0; i < words.length - 1; i++) {
                ansStr += words[i] + ",";
            }
            ansStr += words[words.length - 1];
            results.add(ansStr);
        }

    }


    /**
     * The bolt will only emit the field "word"
     */
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
    }
//		declarer.declare(new Fields("word"));
//	}
}
