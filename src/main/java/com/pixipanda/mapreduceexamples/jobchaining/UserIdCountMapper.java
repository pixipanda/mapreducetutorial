package com.pixipanda.mapreduceexamples.jobchaining;

import java.io.IOException;
import java.util.Map;

import com.pixipanda.mapreduceexamples.utils.MRDPUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class UserIdCountMapper extends Mapper<Object, Text, Text, LongWritable>{

	public static final String AVERAGE_CALC_GROUP = "AverageCalculation";
	public static final String POSTS_COUNTER_NAME = "postCount";
	private static final LongWritable ONE = new LongWritable(1);
	private Text outKey = new Text();
	
	public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
		
		Map<String, String> parsed = MRDPUtils.transformXmlToMap(value.toString());
		String userId =  parsed.get("OwnerUserId");
		
		if (userId != null) {
			
			outKey.set(userId);
			context.write(outKey, ONE);
			context.getCounter(AVERAGE_CALC_GROUP, POSTS_COUNTER_NAME).increment(1);
		}
	}
}
