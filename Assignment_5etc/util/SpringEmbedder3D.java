
//package J4BioInfWS2016.graphview2d;

package util;
/**
 * simple spring embedder
 * Created by huson on 10/31/15.
 */
public class SpringEmbedder3D {
    /**
     * Computes a spring embedding of a graph
     *
     * @param iterations         number of iterations
     * @param numberOfNodes      number of nodes
     * @param edges              edges as pairs of node ids (between 0 and numberOfNodes-1)
     * @param initialCoordinates initial coordinates or null. If given, must not be constant
     */
    public static double[][] computeSpringEmbedding(int iterations, int numberOfNodes, int[][] edges, double[][] initialCoordinates) {
        if (numberOfNodes < 2)
            return new double[][]{{0.0, 0.0, 0.0}};

        final int width; // approximate window width
        final int height; // approximate window height
        final int depth; //approximate window depth

        final double[][] coordinates = new double[numberOfNodes][3];
        if (initialCoordinates != null) {
            System.arraycopy(initialCoordinates, 0, coordinates, 0, initialCoordinates.length);
            double minX = 10000000;
            double maxX = -10000000;
            double minY = 10000000;
            double maxY = -10000000;
            double minZ = 10000000;
            double maxZ = -10000000;

            for (double[] coordinate : coordinates) {
                minX = Math.min(coordinate[0], minX);
                maxX = Math.max(coordinate[0], maxX);
                minY = Math.min(coordinate[1], minY);
                maxY = Math.max(coordinate[1], maxY);
                minZ = Math.min(coordinate[2], minZ);
                maxZ = Math.max(coordinate[2], maxZ);
            }
            width = (int) (maxX - minX);
            height = (int) (maxY - minY);
            depth = (int) (maxZ - minZ);

        } else {
            // position on a circle of radius "width/2"
            width = 200;
            height = 200;
            depth = 200;

            System.out.println("Without an inital embedding, this implementation of the spring embedder does not necessarily make sense.");

            int count = 0;
            for (int v = 0; v < numberOfNodes; v++) {
                coordinates[v][0] = (float) (0.5 * width * Math.sin(2.0 * Math.PI * (double) count / (double) numberOfNodes));
                coordinates[v][1] = (float) (0.5 * height * Math.cos(2.0 * Math.PI * (double) count / (double) numberOfNodes));
                coordinates[v][2] = (float) (0.5 * depth * count);
                count++;
            }
        }

        // compute node degrees
        final int[] deg = new int[numberOfNodes];
        for (int[] e : edges) {
            deg[e[0]]++;
            deg[e[1]]++;
        }

        // run iterations of spring embedding:
        final double log2 = Math.log(2);

        for (int count = 1; count < iterations; count++) {
            final double k = Math.pow(Math.pow(width * height * depth / numberOfNodes, 1. / 3.), 2.);

            final double l2 = 25 * log2 * Math.log(1 + count);

            final double tx = width / l2;
            final double ty = height / l2;
            final double tz = depth / l2;

            final double[][] array = new double[numberOfNodes][3];

            // repulsions
            for (int v = 0; v < numberOfNodes; v++) {
                double xv = coordinates[v][0];
                double yv = coordinates[v][1];
                double zv = coordinates[v][2];

                for (int u = 0; u < numberOfNodes; u++) {
                    if (u == v)
                        continue;
                    double xdist = xv - coordinates[u][0];
                    double ydist = yv - coordinates[u][1];
                    double zdist = zv - coordinates[u][2];
                    double dist = xdist * xdist + ydist * ydist + zdist*zdist;
                    if (dist < 1e-3)
                        dist = 1e-3;
                    double repulse = k * k / dist;
                    array[v][0] += repulse * xdist;
                    array[v][1] += repulse * ydist;
                    array[v][2] += repulse * zdist;
                }

                for (int[] edge : edges) {
                    int a = edge[0];
                    int b = edge[1];
                    if (a == v || b == v)
                        continue;
                    double xdist = xv - (coordinates[a][0] + coordinates[b][0]) / 2;
                    double ydist = yv - (coordinates[a][1] + coordinates[b][1]) / 2;
                    double zdist = zv - (coordinates[a][2] + coordinates[b][2]) / 2;
                    double dist = xdist * xdist + ydist * ydist + zdist * zdist;
                    if (dist < 1e-3) dist = 1e-3;
                    double repulse = k * k / dist;
                    array[v][0] += repulse * xdist;
                    array[v][1] += repulse * ydist;
                    array[v][2] += repulse * zdist;
                }
            }

            // attractions

            for (int[] edge : edges) {
                final int u = edge[0];
                final int v = edge[1];

                double xdist = coordinates[v][0] - coordinates[u][0];
                double ydist = coordinates[v][1] - coordinates[u][1];
                double zdist = coordinates[v][2] - coordinates[v][2];

                double dist = Math.sqrt(xdist * xdist + ydist * ydist + zdist*zdist);

                dist /= ((deg[u] + deg[v]) / 16.0);

                array[v][0] -= xdist * dist / k;
                array[v][1] -= ydist * dist / k;
                array[v][2] -= zdist * dist / k;

                array[u][0] += xdist * dist / k;
                array[u][1] += ydist * dist / k;
                array[u][2] += zdist * dist / k;
            }

            // exclusions

            for (int v = 0; v < numberOfNodes; v++) {
                double xd = array[v][0];
                double yd = array[v][1];
                double zd = array[v][2];

                final double dist = Math.sqrt(xd * xd + yd * yd + zd* zd);

                xd = tx * xd / dist;
                yd = ty * yd / dist;
                zd = tz * zd / dist;

                coordinates[v][0] += xd;
                coordinates[v][1] += yd;
                coordinates[v][2] += zd;
            }
        }
        return coordinates;
    }

    /**
     * place coordinates into a desired rectangle
     *
     * @param coordinates coordinates
     * @param xMin        desired min x
     * @param xMax        desired max x
     * @param yMin        desired min y
     * @param yMax        desired max y
     */
    public static void centerCoordinates(double[][] coordinates, int xMin, int xMax, int yMin, int yMax, int zMin, int zMax) {

        double cxMin = Double.MAX_VALUE;
        double cxMax = Double.MIN_VALUE;
        double cyMin = Double.MAX_VALUE;
        double cyMax = Double.MIN_VALUE;
        double czMin = Double.MAX_VALUE;
        double czMax = Double.MIN_VALUE;

        for (double[] apt : coordinates) {
            cxMin = Math.min(cxMin, apt[0]);
            cxMax = Math.max(cxMax, apt[0]);
            cyMin = Math.min(cyMin, apt[1]);
            cyMax = Math.max(cyMax, apt[1]);
            czMin = Math.min(czMin, apt[2]);
            czMax = Math.max(czMax, apt[2]);
        }

        if ((cxMax - cxMin) != 0 && (cyMax - cyMin) != 0) {
            final double factor = Math.min(Math.min(((xMax - xMin) / (cxMax - cxMin)), ((yMax - yMin) / (cyMax - cyMin))), ((zMax - zMin)
                    / (czMax - czMin)));
            final double dX = ((xMax + xMin - factor * (cxMax + cxMin)) / 2);
            final double dY = ((yMax + yMin - factor * (cyMax + cyMin)) / 2);
            final double dZ = ((zMax + zMin - factor * (czMax + czMin)) / 2);

            for (double[] apt : coordinates) {
                apt[0] = factor * apt[0] + dX;
                apt[1] = factor * apt[1] + dY;
                apt[2] = factor * apt[2] + dZ;
            }
        }
    }
}


