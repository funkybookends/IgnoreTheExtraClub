package com.ignoretheextraclub.vanillasiteswap.thros;

/**
 * Created by caspar on 24/12/16.
 */
public class VanillaThro extends AbstractThro implements Comparable
{
    final int thro;

    public VanillaThro(int thro)
    {
        this.thro = thro;
    }

    public int getThro()
    {
        return thro;
    }

    @Override
    public String toString()
    {
        return String.valueOf(thro);
    }

    @Override
    public int compareTo(Object o)
    {
        return ((VanillaThro) o).getThro() - this.getThro();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VanillaThro that = (VanillaThro) o;

        return thro == that.thro;
    }

    @Override
    public int hashCode()
    {
        return thro;
    }
}
