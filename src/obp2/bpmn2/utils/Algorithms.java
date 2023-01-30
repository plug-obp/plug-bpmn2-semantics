package obp2.bpmn2.utils;

import javafx.util.Pair;

import java.util.*;
import java.util.function.Function;

public class Algorithms {

    private Algorithms() {}

    static public <T, S extends Collection<T>> List<List<T>> cartesianProduct(Collection<S> domainSet) {
        if (domainSet.isEmpty()) return Collections.emptyList();
        List<List<T>> result = new LinkedList<>();
        result.add(Collections.emptyList());
        for (S domain : domainSet) {
            List<List<T>> previousProductList = result;
            result = new LinkedList<>();
            for (List<T> previousProduct : previousProductList) {
                for (T element : domain) {
                    List<T> product = new LinkedList<>(previousProduct);
                    product.add(element);
                    result.add(product);
                }
            }
        }
        return result;
    }

    static private int[][] EMPTY_PRODUCT = new int[][]{};

    /**
     * <p>Computes the cartesian products of the array of domains provided as argument.</p>
     *
     * <p>Some examples:
     * <ul>
     *     <li><code>x{..., {}, ...}} = {} // One empty domain means an empty cartesian product</li>
     *     <li><code>x{{1, 2, 3}} = {{1}, {2}, {3}}</code></li>
     *     <li><code>x{{1}, {2}, {3}} = {{1, 2, 3}}</code></li>
     *     <li><code>x{{1}, {2, 3}} = {{1, 2}, {1, 3}}</code></li>
     * </ul></p>
     *
     * <p>It preserves the ordering of domains and elements in each domain.
     * For instance, the first combination will have the first element of each domain.</p>
     *
     * @param domains an array of domains, each domain being an array of int
     * @return cardinal product of domains
     */
    static public int[][] cartesianProduct(int[][] domains) {
        if (domains.length == 0) return EMPTY_PRODUCT;
        int nbCombinations = 0;
        for (int[] domain : domains) {
            int domainSize = domain.length;
            if (domainSize == 0) return EMPTY_PRODUCT;
            nbCombinations = nbCombinations == 0 ? domainSize : nbCombinations * domainSize;
        }
        int[][] product = new int[nbCombinations][];
        int[] domain = domains[0];
        int domainSpan = nbCombinations / domain.length;
        for (int i = 0; i < nbCombinations; i++) {
            product[i] = new int[domains.length];
            product[i][0] = domain[(i/domainSpan) % domain.length];
        }
        for (int domainIndex = 1; domainIndex < domains.length; domainIndex++) {
            domain = domains[domainIndex];
            domainSpan = domainSpan / domain.length;
            for (int i = 0; i < nbCombinations; i++) {
                product[i][domainIndex] = domain[(i/domainSpan) % domain.length];
            }
        }
        return product;
    }

    /**
     * <p>Remove and insert indexes to an array of indexes, following natural order of integers.
     * It also provides a mechanism to keep track of the changes, see below.</p>
     *
     * <p>Takes five arrays as arguments, two of them are used to store the result.
     * For instance, after the call <code>newIndexes</code> holds the new indexes
     * and <code>newPositions</code> holds the shift in positions for old indexes.
     * As such, <code>newIndexes</code> must be of length
     * <code>oldIndexes.length - positionsToRemove.length + indexesToAdd.length</code>
     * and <code>newPositions</code> must be of length <code>oldIndexes.length</code>.</p>
     *
     * <p><b>Positions</b> to remove (values in <code>positionsToRemove</code>) <b>must range between
     * 1</b> (pointing to the index at the first position) <b>and <code>oldIndexes.length</code></b>
     * (pointing to the index at the last position), both included.</p>
     *
     * <p>Similarly, positions in <code>newPositions</code> will range from 1 to <code>newIndexes.length</code>
     * if the matching index in <code>oldIndexes</code> is still present in <code>newIndexes</code> (and might
     * have moved, i.e. changed its position, due to other indexes being inserted or removed). <b>If removed, the
     * new position in <code>newPositions</code> will be equal to 0.</b></p>
     *
     * <p><b>Provided arrays</b> <code>oldIndexes</code>, <code>positionsToRemove</code> and
     * <code>indexesToAdd</code> <b>must be sorted.</b></p>
     *
     * <p>If an added index is already present, the new index is inserted as late as possible.
     * Although equal, the old index position will point to an earlier position than the added index.</p>
     *
     * @param newIndexes Empty array of length <code>oldIndexes.length - positionsToRemove.length + indexesToAdd.length</code>
     * @param newPositions Empty array of length <code>oldIndexes.length</code>
     * @param oldIndexes Sorted array of indexes
     * @param positionsToRemove Sorted array of positions to remove
     * @param indexesToAdd Sorted array of indexes to insert
     */
    static public void removeInsertAndTraceIndexes(
            int[] newIndexes, int[] newPositions,
            int[] oldIndexes, int[] positionsToRemove, int[] indexesToAdd
    ) {
        int nextOld = oldIndexes.length > 0 ? oldIndexes[0] : Integer.MAX_VALUE;
        int nextRemove = positionsToRemove.length > 0 ? positionsToRemove[0] - 1 : Integer.MAX_VALUE;
        int nextAdd = indexesToAdd.length > 0 ? indexesToAdd[0] : Integer.MAX_VALUE;
        int iNew = 0, iOld = 0, iRemove = 0, iAdd = 0;
        while (iNew < newIndexes.length) {
            if (nextRemove == iOld) {
                newPositions[iOld] = 0;
                iOld += 1;
                nextOld = oldIndexes.length > iOld ? oldIndexes[iOld] : Integer.MAX_VALUE;
                iRemove += 1;
                nextRemove = iRemove < positionsToRemove.length ? positionsToRemove[iRemove] - 1 : Integer.MAX_VALUE;
                continue;
            }
            if (nextAdd < nextOld) {
                newIndexes[iNew] = nextAdd;
                iNew += 1;
                iAdd += 1;
                nextAdd = iAdd < indexesToAdd.length ? indexesToAdd[iAdd] : Integer.MAX_VALUE;
                continue;
            }
            newIndexes[iNew] = nextOld;
            iNew += 1;
            newPositions[iOld] = iNew;
            iOld += 1;
            nextOld = oldIndexes.length > iOld ? oldIndexes[iOld] : Integer.MAX_VALUE;
        }
    }

    /**
     * <p>Maps all elements from the provided collection to their index and returns a sorted array with those.</p>
     *
     * <p>It likely could be optimized, but it is only used when loading the model.</p>
     */
    static public <T> int[] getSortedIndexArray(Function<T, Integer> indexFunction, Collection<T> collection) {
        int[] result = new int[collection.size()];
        int i = 0;
        for (T object : collection) {
            result[i++] = indexFunction.apply(object);
        }
        Arrays.sort(result);
        return result;
    }

    static public <T> void biSort(int[] toSortArray, T[] toMirrorArray) {
        if (toSortArray.length != toMirrorArray.length)
            throw new IllegalArgumentException("toSortArray.length != toMirrorArray.length");
        List<Pair<Integer, T>> pairList = new LinkedList<>();
        for (int i = 0; i < toSortArray.length; i++) {
            pairList.add(new Pair<>(toSortArray[i], toMirrorArray[i]));
        }
        pairList.sort(Comparator.comparingInt(Pair::getKey));
        int index = 0;
        for (Pair<Integer, T> pair : pairList) {
            toSortArray[index] = pair.getKey();
            toMirrorArray[index] = pair.getValue();
            index += 1;
        }
    }

}
