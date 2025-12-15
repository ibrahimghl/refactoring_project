package re.forestier.edu.rpg;

import re.forestier.edu.lib.Natural;

public class Item implements Cloneable
{
	private String name;
	private String description;
	private Natural weight;
	private Natural value;

	public Item(String name, String description, Natural weight, Natural value)
	{
		this.name = name;
		this.description = description;
		this.weight = weight;
		this.value = value;
	}

	Item(String name)
	{
		this.name = name;
		this.description = "Is used to search in list, if you see me, there is a problem";
	}

	public String getName()
	{
		return this.name;
	}

	public String getDescription()
	{
		return this.description;
	}

	public Natural getWeight()
	{
		return (Natural)this.weight.clone();
	}

	public Natural getValue()
	{
		return (Natural)this.value.clone();
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder(this.name);
		sb.append(" :\n");
		sb.append(this.description);
		sb.append("\nWeight : ");
		sb.append(this.weight.toString());
		sb.append("\nValue : ");
		sb.append(this.value.toString());
		return sb.toString();
	}

	@Override
	public Object clone()
	{
		return new Item(this.name,this.description,(Natural)this.weight.clone(),(Natural)this.value.clone());
	}

	@Override
	public boolean equals(Object o)
	{
		if(o != null && o instanceof Item && ((Item)o).getName() == this.name)
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
}