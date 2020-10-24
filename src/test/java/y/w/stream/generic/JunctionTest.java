package y.w.stream.generic;

import org.junit.Test;

public class JunctionTest {
    @Test
    public void junctionsTest() {
        // Fan-Out
        // Broadcast<T> - 1 input, N outputs, incoming is emitted to all outputs
        // Balance<T>   - 1 input, N outputs, incoming is emitted to one outputs

        // UnzipWith<In, A, B, ...> - 1 input, N outputs. A function converts one
        // input and sends it to N outputs.

        // Unzip<A, B> - 1 input, 2 outputs - splits a stream of Pair<A, B> into
        // two streams of A and B.

        // Fan-In
        // Merge<In> - N inputs and 1 output
        // MergePreferred<In>
        // ZipWith<A, B, ..., Out> - N inputs and 1 output : uses a function to
        // take one element from each input and convert them all into a single
        // output.

        // Zip<A,B> Zips two streams of A and B into single stream of Pair<A, B>.
        // Concat<A>
    }
}
