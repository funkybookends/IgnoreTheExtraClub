package com.ignoretheextraclub.vanillasiteswap.generators;

import com.ignoretheextraclub.vanillasiteswap.state.AbstractState;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by caspar on 11/12/16.
 */
public class StateSearchGenerator<State extends AbstractState> implements Runnable
{
    private final int resultLimit;
    private final int finalPeriod;
    private final boolean allRotations;

    /**
     * The predicates will be examined at every level of the search.
     * So when recieveing a State[] of size n, you can be assured it was accepted with size n-1.
     * You are encouraged to keep these cheap.
     */
    private final List<Predicate<State[]>> predicates;
    private final Set<State> startingStates;
    private final Consumer<State[]> consumer;

    private int results;

    public StateSearchGenerator(final int resultLimit,
                                final int finalPeriod,
                                final boolean allRotations,
                                final List<Predicate<State[]>> predicates,
                                final Set<State> startingStates,
                                final Consumer<State[]> consumer)
    {
        this.resultLimit = resultLimit;
        this.finalPeriod = finalPeriod;
        this.allRotations = allRotations;
        this.predicates = predicates;
        this.startingStates = startingStates;
        this.consumer = consumer;
        results = 0;
    }

    @Override
    public void run()
    {
        for (State startingState : startingStates)
        {
            Stack<State> path = new Stack<>();
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

    private boolean acceptable(final State[] candidate)
    {
        for (Predicate<State[]> predicate : predicates)
        {
            if (!predicate.test(candidate))
            {
                return false;
            }
        }
        return true;
    }

    private void generate(Stack<State> path) throws ResultLimitReached
    {
        if (path.size() < finalPeriod)
        {
            for (AbstractState abstractChild : path.peek().getNextStates())
            {
                path.push((State) abstractChild);
                if (acceptable(getPathAsStateArray(path))) generate(path);
            }
        }
        else if (path.size() == finalPeriod &&
                 path.lastElement().canTransition(path.firstElement()))
        {
            consumer.accept(getPathAsStateArray(path));
            if (++results >= resultLimit) throw new ResultLimitReached();

        }
        else if (path.size() > finalPeriod) throw new RuntimeException("Period should never be longer than final period");
        path.pop();
    }

    @SuppressWarnings("unchecked")
    private State[] getPathAsStateArray(Stack<State> path)
    {
        return path.toArray((State[]) Array.newInstance(path.lastElement().getClass().getComponentType(), path.size()));
    }

    private class ResultLimitReached extends Exception {}
}
