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

  public EveryNIterationWriter(final int every, final Evaluator otherEvaluator, final CRF model,
      final String basePath) {
    this.every = every;
    this.otherEvaluator = otherEvaluator;
    this.model = model;
    this.basePath = basePath;
    current=0;
  }
  public EveryNIterationWriter(final int every, final CRF model, final String basePath) {
    this.every = every;
    this.model = model;
    this.basePath = basePath;
    otherEvaluator = null;
    current=0;
  }

  @Override
  public boolean evaluate() {
    current++;
    if (every!=0 && current%every==0) {
      try {
        model.write(basePath+"."+current);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return (otherEvaluator==null || otherEvaluator.evaluate());
  }
}
