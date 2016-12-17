package com.ignoretheextraclub.vanillasiteswap.siteswap;

import com.ignoretheextraclub.vanillasiteswap.converters.GlobalLocal;
import com.ignoretheextraclub.vanillasiteswap.converters.IntPrechac;
import com.ignoretheextraclub.vanillasiteswap.converters.IntVanilla;
import com.ignoretheextraclub.vanillasiteswap.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.vanillasiteswap.sorters.VanillaStateSorter;
import com.ignoretheextraclub.vanillasiteswap.sorters.impl.FourHandedPassingStrategy;
import com.ignoretheextraclub.vanillasiteswap.state.VanillaState;

/**
 * Created by caspar on 14/12/16.
 */
class FourHandedSiteswapBuilder extends VanillaSiteswapBuilder
{
    private int[] leaderIntSiteswap;
    private int[] followerIntSiteswap;
    private String leaderStringSiteswap;
    private String followerStringSiteswap;
    private String leaderPrechac;
    private String followerPrechac;
    private boolean isMirrored;

    public FourHandedSiteswapBuilder(int[] vanillaSiteswap, VanillaStateSorter sortingStrategy) throws InvalidSiteswapException
    {
        super(vanillaSiteswap,4, sortingStrategy);
        initFourHandedSiteswapFields();
    }

    public FourHandedSiteswapBuilder(VanillaState[] states) throws InvalidSiteswapException
    {
        super(states, 4, VanillaSiteswap.DEFAULT_SORTING_STRATEGY);
        initFourHandedSiteswapFields();
    }

    private void initFourHandedSiteswapFields()
    {
        isMirrored = (intSiteswap.length % 2 == 1);
        leaderIntSiteswap = GlobalLocal.globalToLocal(this.intSiteswap, 0);
        followerIntSiteswap = GlobalLocal.globalToLocal(this.intSiteswap, 1);
        leaderStringSiteswap = IntVanilla.intArrayToString(leaderIntSiteswap);
        followerStringSiteswap = IntVanilla.intArrayToString(followerIntSiteswap);
        leaderPrechac = IntPrechac.fourHandedIntsToPrechac(leaderIntSiteswap);
        followerPrechac = IntPrechac.fourHandedIntsToPrechac(followerIntSiteswap);
    }

    protected FourHandedSiteswap buildFourHandedSiteswap() throws InvalidSiteswapException
    {
        if (startingObjectsPerHand.length > 4)
        {
            throw new InvalidSiteswapException("No humans have more than two hands!");
        }
        if (this.highestThrow > FourHandedSiteswap.MAX_THROW)
            throw new InvalidSiteswapException("Four Handed Siteswaps Cannot have throws larger than " + FourHandedSiteswap.MAX_THROW);

        for (int thro : intSiteswap)
            for (int illegalThrow : FourHandedSiteswap.ILLEGAL_THROWS)
                if (thro == illegalThrow)
                    throw new InvalidSiteswapException("Throw [" + thro + "] is illegal in Four Handed Siteswaps");

        return new FourHandedSiteswap(2, period, numObjects, intSiteswap, states, prime,
                                      grounded, highestThrow, startingObjectsPerHand, stringSiteswap,
                                      sortingStrategy.getName(), isMirrored, leaderIntSiteswap, leaderStringSiteswap,
                                      leaderPrechac, followerIntSiteswap, followerStringSiteswap, followerPrechac);
    }
}
