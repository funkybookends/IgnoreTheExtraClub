package com.ignoretheextraclub.itec.ui;

public class PatternRequest
{
	private String siteswap;
	private String type;

	public PatternRequest()
	{
	}

	public PatternRequest(final String siteswap,
	                      final String type)
	{
		this.siteswap = siteswap;
		this.type = type;
	}

	public void setSiteswap(final String siteswap)
	{
		this.siteswap = siteswap;
	}

	public void setType(final String type)
	{
		this.type = type;
	}

	public String getSiteswap()
	{
		return siteswap;
	}

	public String getType()
	{
		return type;
	}

	@Override
	public String toString()
	{
		return "PatternRequest{" +
			"siteswap='" + siteswap + '\'' +
			", type='" + type + '\'' +
			'}';
	}

	@Override
	public boolean equals(final Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		final PatternRequest that = (PatternRequest) o;

		if (siteswap != null ? !siteswap.equals(that.siteswap) : that.siteswap != null) return false;
		return type != null ? type.equals(that.type) : that.type == null;
	}

	@Override
	public int hashCode()
	{
		int result = siteswap != null ? siteswap.hashCode() : 0;
		result = 31 * result + (type != null ? type.hashCode() : 0);
		return result;
	}
}
