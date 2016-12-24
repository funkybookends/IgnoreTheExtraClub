package com.ignoretheextraclub.vanillasiteswap.generators;

import com.ignoretheextraclub.vanillasiteswap.exceptions.*;
import com.ignoretheextraclub.vanillasiteswap.siteswap.VanillaSiteswap;
import com.ignoretheextraclub.vanillasiteswap.sorters.SortingUtils;
import com.ignoretheextraclub.vanillasiteswap.sorters.StateSorter;
import com.ignoretheextraclub.vanillasiteswap.sorters.impl.HighestThrowFirstStrategy;
import com.ignoretheextraclub.vanillasiteswap.state.VanillaState;
import org.apache.commons.lang.NotImplementedException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by caspar on 11/12/16.
 */
public class StateSearchGenerator
{
    private static final StateSorter DEFAULT_SORTER = new HighestThrowFirstStrategy();

    private final int resultLimit;
    private final int finalPeriod;
    private boolean prime = false;
    private boolean allRotations = false;
    private final Set<VanillaState> bannedStates = new HashSet<>();
    private final Set<Integer> bannedThrows = new HashSet<>();
    private final Set<VanillaState> startingStates = new HashSet<>();
    private Collection<Result<VanillaState>> results;
    private StateSorter sorter = DEFAULT_SORTER;

    public StateSearchGenerator(final int resultLimit, final int finalPeriod, final int maxThrow, final int numObjects)
        throws InvalidSiteswapException
    {
        try
        {
            this.resultLimit = resultLimit;
            this.finalPeriod = VanillaSiteswap.validatePeriod(finalPeriod);
            this.startingStates.add(VanillaState.getGroundState(maxThrow, numObjects));
        }
        catch (final PeriodException | StateSizeException | NumObjectsException e)
        {
            throw new InvalidSiteswapException("Cannot genereate, they will be invalid siteswaps", e);
        }
    }

    public StateSearchGenerator banThrows(int... thros)
    {
        if (results != null) throw new UnsupportedOperationException("Already Generated");
        this.bannedThrows.addAll(Arrays.stream(thros).boxed().collect(Collectors.toSet()));
        return this;
    }

    public StateSearchGenerator banThrows(Set<Integer> thros)
    {
        if (results != null) throw new UnsupportedOperationException("Already Generated");
        this.bannedThrows.addAll(thros);
        return this;
    }

    public StateSearchGenerator banThrow(int thro)
    {
        if (results != null) throw new UnsupportedOperationException("Already Generated");
        this.bannedThrows.add(thro);
        return this;
    }

    public StateSearchGenerator banState(VanillaState bannedState)
    {
        if (results != null) throw new UnsupportedOperationException("Already Generated");
        this.bannedStates.add(bannedState);
        return this;
    }

    public StateSearchGenerator primeOnly()
    {
        this.prime = true;
        return this;
    }

    public StateSearchGenerator allRotations()
    {
        this.allRotations = true;
        return this;
    }

    public StateSearchGenerator nonGround()
    {
        if (startingStates.size() == 1)
        {
            VanillaState ground = ((VanillaState[]) startingStates.toArray())[0];
        }
//        startingStates.clear();
        throw new NotImplementedException();
    }

    public StateSearchGenerator overrideSorter(final StateSorter sorter)
    {
        this.sorter = sorter;
        return this;
    }

    public int getNumResults()
    {
        if (results == null) throw new UnsupportedOperationException("Must generate first!");
        return results.size();
    }

    private void generate() throws StateSizeException, NumObjectsException
    {
        if (allRotations) results = new LinkedList<>();
        else results = new HashSet<>();
        for (VanillaState startingState : startingStates)
        {
            Stack<VanillaState> path = new Stack<>();
            path.push(startingState);
            try
            {
                generate(path);
            }
            catch (ResultLimitReached resultLimitReached)
            {
                break;
            }
        }
    }

    private void generate(Stack<VanillaState> path) throws ResultLimitReached
    {
        if (path.size() == finalPeriod)
        {
            try
            {

                if (!bannedThrows.contains(VanillaState.transition(path.lastElement(), path.firstElement())))
                {
                    VanillaState[] result = new VanillaState[finalPeriod];
                    path.toArray(result);
                    if (!allRotations)
                    {
                        try
                        {
                            result = SortingUtils.sort(result, sorter);
                        }
                        catch (InvalidSiteswapException e)
                        {
                            throw new RuntimeException("Could not sort what should be a valid siteswap", e);
                        }
                    }
                    Result<VanillaState> resultObject = new Result<>(result);
                    results.add(resultObject);
                    if (results.size() >= resultLimit) throw new ResultLimitReached();
                }
            }
            catch (final NoTransitionException ignored)
            {
                // do nothing, not a valid siteswap
            }
        }
        else
        {
            VanillaState parent = path.peek();
            try
            {
                for (int thro : parent.getAvailableThrows())
                {
                    if (!bannedThrows.contains(thro))
                    {
                        VanillaState child = getChild(parent, thro);
                        if (!bannedStates.contains(child))
                        {
                            if (!(prime && path.contains(child)))
                            {
                                path.push(child);
                                generate(path);
                            }
                        }
                    }
                }
            }
            catch (final BadThrowException e)
            {
                throw new RuntimeException("VanillaSiteswap(" + parent + ").getAvailableThrows() returned a bad throw.", e);
            }
        }
        path.pop();
    }

    private Map<VanillaState, Map<Integer, VanillaState>> childrenCache = new HashMap<>();

    private VanillaState getChild(VanillaState parent, int thro) throws BadThrowException
    {
        if (!childrenCache.containsKey(parent))
        {
            childrenCache.put(parent, new TreeMap<>());
        }
        Map<Integer, VanillaState> children = childrenCache.get(parent);
        if (!children.containsKey(thro))
        {
            children.put(thro, parent.thro(thro));
        }
        return children.get(thro);
    }

    public Collection<VanillaState[]> generateToCollectionOfVanillaStateArray() throws StateSizeException, NumObjectsException
    {
        if (results == null) generate();
        return results.stream().map(Result::getStates).collect(Collectors.toList());
    }

    private class ResultLimitReached extends Throwable {}

    private class Result<T>
    {
        private final T[] states;

        public Result(T[] states)
        {
            this.states = states;
        }

        public T[] getStates()
        {
            return states;
        }

        @Override
        public boolean equals(Object o)
        {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Result<?> result = (Result<?>) o;

            // Probably incorrect - comparing Object[] arrays with Arrays.equals
            return Arrays.equals(states, result.states);
        }

        @Override
        public int hashCode()
        {
            return Arrays.hashCode(states);
        }
    }
}
