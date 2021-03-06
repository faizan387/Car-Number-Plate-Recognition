package org.ea.javacnn.trainers;

import org.ea.javacnn.JavaCNN;
import org.ea.javacnn.data.BackPropResult;
import org.ea.javacnn.data.DataBlock;
import org.ea.javacnn.data.TrainResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Trainers take the generated output of activations and gradients in
 * order to modify the weights in the network to make a better prediction
 * the next time the network runs with a data block.
 *
 * @author Daniel Persson (mailto.woden@gmail.com)
 */
public abstract class Trainer {
    private JavaCNN net;
    protected double learning_rate, l1_decay, l2_decay;
    protected int batch_size, k;
    protected double momentum, eps;
    protected List<double[]> gsum, xsum;

    public Trainer(JavaCNN net, int batch_size, float l2_decay) {
        this.net = net;

        this.learning_rate = 0.01;
        this.l1_decay = 0.001;
        this.l2_decay = l2_decay;
        this.batch_size = batch_size;
        this.momentum = 0.9;
        this.eps = 1e-8;

        gsum = new ArrayList<double[]>();
        xsum = new ArrayList<double[]>();

        this.k = 0; // iteration counter
    }

    public TrainResult train(DataBlock x, int y) {
        this.net.forward(x, true); // also set the flag that lets the net know we're just training

        double cost_loss = this.net.backward(y);
        double l2_decay_loss = 0.0;
        double l1_decay_loss = 0.0;

        this.k++;
        if(this.k % this.batch_size == 0) {

            List<BackPropResult> pglist = this.net.getBackPropagationResult();

            // initialize lists for accumulators. Will only be done once on first iteration
            if(this.gsum.size() == 0 && this.momentum > 0.0) {
                for(int i=0;i<pglist.size();i++) {
                    double[] newGsumArr = new double[pglist.get(i).getWeights().length];
                    Arrays.fill(newGsumArr, 0);
                    this.gsum.add(newGsumArr);
                    initTrainData(pglist.get(i));
                }
            }

            // perform an update for all sets of weights
            for(int i=0;i<pglist.size();i++) {
                BackPropResult pg = pglist.get(i); // param, gradient, other options in future (custom learning rate etc)
                double[] p = pg.getWeights();
                double[] g = pg.getGradients();

                // learning rate for some parameters.
                double l2_decay_mul = pg.getL2DecayMul();
                double l1_decay_mul = pg.getL1DecayMul();
                double l2_decay = this.l2_decay * l2_decay_mul;
                double l1_decay = this.l1_decay * l1_decay_mul;

                int plen = p.length;
                for(int j=0;j<plen;j++) {
                    l2_decay_loss += l2_decay*p[j]*p[j]/2; // accumulate weight decay loss
                    l1_decay_loss += l1_decay*Math.abs(p[j]);
                    double l1grad = l1_decay * (p[j] > 0 ? 1 : -1);
                    double l2grad = l2_decay * (p[j]);

                    double gij = (l2grad + l1grad + g[j]) / this.batch_size; // raw batch gradient

                    update(i, j, gij, p);

                    g[j] = 0.0; // zero out gradient so that we can begin accumulating anew
                }
            }
        }

        // appending softmax_loss for backwards compatibility, but from now on we will always use cost_loss
        // in future, TODO: have to completely redo the way loss is done around the network as currently
        // loss is a bit of a hack. Ideally, user should specify arbitrary number of loss functions on any layer
        // and it should all be computed correctly and automatically.
        return new TrainResult(0, 0, l1_decay_loss, l2_decay_loss, cost_loss, cost_loss, cost_loss + l1_decay_loss + l2_decay_loss);
    }

    public abstract void update(int i, int j, double gij, double[] p);

    public void initTrainData(BackPropResult bpr) {};
}
