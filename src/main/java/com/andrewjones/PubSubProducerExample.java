package com.andrewjones;

import com.andrewjones.avro.customers;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubIO;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.values.PCollection;

import java.util.ArrayList;
import java.util.List;

public class PubSubProducerExample {
    public static void main(String[] args){

        PubsubOptions options = PipelineOptionsFactory.as(PubsubOptions.class);
        options.setPubsubRootUrl("http://pubsub:8042");
        Pipeline p = Pipeline.create(options);

        // sample data
        List<customers> data = new ArrayList<>();
        data.add(new customers(1, "Sally","Thomas","sally.thomas@acme.com"));
        data.add(new customers(2, "George","Bailey","gbailey@foobar.com"));
        data.add(new customers(3, "Edward","Walker","ed@walker.com"));
        data.add(new customers(4, "Anne","Kretchmar","annek@noanswer.org"));

        PCollection<customers> input = p.apply(Create.of(data));

        input.apply(PubsubIO.writeAvros(customers.class).to("projects/project-id/topics/customers"));

        p.run().waitUntilFinish();
    }
}
