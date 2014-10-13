package iitb.Segment;

import iitb.CRF.*;

import java.io.*;

/**
 * Created by Maximian Rudolph on 13.10.14.
 */
public class EveryNIterationWriter implements Evaluator {

  private final int every;
  private final Evaluator otherEvaluator;
  private int current;
  private final CRF model;
  private final String basePath;
  private final boolean logWrites;
  public EveryNIterationWriter(final int every, final Evaluator otherEvaluator, final CRF model,
      final String basePath, final boolean logWrites) {
    this.every = every;
    this.otherEvaluator = otherEvaluator;
    this.model = model;
    this.basePath = basePath;
    this.logWrites = logWrites;
    current=0;
  }
  public EveryNIterationWriter(final int every, final CRF model, final String basePath,
      final boolean logWrites) {
    this.every = every;
    this.model = model;
    this.basePath = basePath;
    this.logWrites = logWrites;
    otherEvaluator = null;
    current=0;
  }

  @Override
  public boolean evaluate() {
    if (every!=0 && current%every==0) {
      try {
        final String path = basePath+"."+current;
        model.write(path);
        if (logWrites) {
          Util.printDbg("Written model to " + path);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    current++;
    return (otherEvaluator==null || otherEvaluator.evaluate());
  }
}
