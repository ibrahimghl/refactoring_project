package re.forestier.edu.lib;

public class Natural implements Comparable<Natural>, Cloneable
{
    private int value;
    public static final Natural ZERO = new ZERO();

    public Natural()
    {
        this.value = 0;
    }

    private Natural(Integer i)
    {
        if(i == null)
            throw new IllegalArgumentException();
        if(i < 0)
            throw new IllegalArgumentException("Natural >=0");
        this.value = i;
    }

    private Natural(int i)
    {
        if(i < 0)
            throw new IllegalArgumentException("Natural >=0");
        this.value = i;
    }

    public Integer toInteger()
    {
        return Integer.valueOf(this.value);
    }

    public int toInt()
    {
        return this.value;
    }

    public static Natural valueOf(Integer i)
    {
        if(i == null)
            throw new IllegalArgumentException();
        return new Natural(i);
    }

    public static Natural valueOf(int i)
    {
        return new Natural(i);
    }

    public void add(Natural n)
    {
        if(n == null)
            throw new IllegalArgumentException();
        this.value += n.toInt();
    }


    public void substract(Natural n)
    {
        if(n == null || this.compareTo(n) == 1)
            throw new IllegalArgumentException();
        this.value -= n.toInt();
    } 

    @Override
    public Object clone()
    {
        return Natural.valueOf(this.value);
    }

    @Override
    public String toString()
    {
        return String.valueOf(this.value);
    }

    @Override
    public boolean equals(Object o)
    {
        if(o == null)
            return false;
        if(o == this)
            return true;
        if(!(o instanceof Natural))
            return false;
        return this.value == ((Natural)o).value;
    }

    @Override
    public int compareTo(Natural n)
    {
        if(n == null)
            throw new IllegalArgumentException();
        if(n.value > this.value)
            return 1;
        else if(n.value == this.value)
            return 0;
        else
            return -1;
    }

    private static class ZERO extends Natural
    {
        private ZERO()
        {
            super(0);
        }
    }
}