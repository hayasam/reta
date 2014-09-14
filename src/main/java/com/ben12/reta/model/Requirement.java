// Package : com.ben12.reta.model
// File : Requirement.java
// 
// Copyright (C) 2014 Beno�t Moreau (ben.12)
//
// This file is part of RETA (Requirement Engineering Traceability Analysis).
//
// RETA is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// RETA is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with RETA.  If not, see <http://www.gnu.org/licenses/>.

package com.ben12.reta.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;

/**
 * @author Beno�t Moreau (ben.12)
 */
public class Requirement implements Comparable<Requirement>
{
	/** Name of requirement attribute "Text". */
	public static final String				ATTRIBUTE_TEXT		= "Text";

	/** Name of requirement attribute "Id". */
	public static final String				ATTRIBUTE_ID		= "Id";

	/** Name of requirement attribute "Version". */
	public static final String				ATTRIBUTE_VERSION	= "Version";

	/** Requirement document source. */
	private final InputRequirementSource	source;

	/** Requirement identifying. */
	private String							id;

	/** Requirement version. */
	private String							version				= "";

	/** Requirement human text. */
	private String							text				= "";

	/** Requirement content description. */
	private String							content				= "";

	/** Requirement extra attributes name and value. */
	private Map<String, String>				attributes			= null;

	/** Set of requirements cover by this requirement. */
	private Set<Requirement>				references			= null;

	/** Set of requirements covering this requirement. */
	private Set<Requirement>				referredBy			= null;

	/**
	 * Build an undefined source requirement. (ex: requirement reference unknown)
	 */
	public Requirement()
	{
		this.source = null;
	}

	/**
	 * @param source
	 *            requirement document source
	 */
	public Requirement(InputRequirementSource source)
	{
		this.source = source;
	}

	/**
	 * @return requirement document source or null if unknown
	 */
	public InputRequirementSource getSource()
	{
		return source;
	}

	/**
	 * @return requirement identifying
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * @param id
	 *            requirement identifying
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * @return requirement version, may be null
	 */
	public String getVersion()
	{
		return version;
	}

	/**
	 * @param version
	 *            requirement version
	 */
	public void setVersion(String version)
	{
		this.version = version;
	}

	/**
	 * @return requirement human text
	 */
	public String getText()
	{
		return text;
	}

	/**
	 * @param text
	 *            requirement human text
	 */
	public void setText(String text)
	{
		this.text = text;
	}

	/**
	 * @return requirement content description
	 */
	public String getContent()
	{
		return content;
	}

	/**
	 * @param content
	 *            requirement content description
	 */
	public void setContent(String content)
	{
		this.content = content;
	}

	/**
	 * @param name
	 *            attribute name
	 * @return attribute value
	 */
	public String getAttribute(String name)
	{
		String att = null;
		switch (name)
		{
		case ATTRIBUTE_TEXT:
			att = getText();
			break;
		case ATTRIBUTE_ID:
			att = getId();
			break;
		case ATTRIBUTE_VERSION:
			att = getVersion();
			break;
		default:
			if (attributes != null)
			{
				att = attributes.get(name);
			}
			break;
		}
		return att;
	}

	/**
	 * @param name
	 *            attribute name
	 * @param value
	 *            attribute value
	 */
	public void putAttribute(String name, String value)
	{
		switch (name)
		{
		case ATTRIBUTE_TEXT:
			setText(value);
			break;
		case ATTRIBUTE_ID:
			setId(value);
			break;
		case ATTRIBUTE_VERSION:
			setVersion(value);
			break;
		default:
			if (attributes == null && value != null)
			{
				attributes = new HashMap<>(1);
			}
			if (attributes != null)
			{
				this.attributes.put(name, value);
			}
			break;
		}
	}

	/**
	 * @param reference
	 *            requirement reference
	 */
	public void addReference(Requirement reference)
	{
		if (references == null)
		{
			references = new TreeSet<>();
		}
		// replace reference
		references.remove(reference);
		references.add(reference);
	}

	/**
	 * @return reference count
	 */
	public int getReferenceCount()
	{
		return (references == null ? 0 : references.size());
	}

	/**
	 * @return requirement reference iterable
	 */
	public List<Requirement> getReferences()
	{
		return (references == null ? new ArrayList<Requirement>(0) : new ArrayList<Requirement>(references));
	}

	/**
	 * @return requirement reference iterable
	 */
	public List<Requirement> getReferencesFor(InputRequirementSource source)
	{
		return (references == null ? new ArrayList<Requirement>(0) : references.stream()
				.filter((r) -> (r.getSource() == source))
				.distinct()
				.collect(Collectors.toList()));
	}

	/**
	 * @param reference
	 *            requirement referencing this requirement
	 */
	public void addReferredBy(Requirement reference)
	{
		if (referredBy == null)
		{
			referredBy = new TreeSet<>();
		}
		// replace reference
		referredBy.remove(reference);
		referredBy.add(reference);
	}

	/**
	 * @return requirement referencing this requirement count
	 */
	public int getReferredByCount()
	{
		return (referredBy == null ? 0 : referredBy.size());
	}

	/**
	 * @return requirement referencing this requirement iterable
	 */
	public List<Requirement> getReferredByRequirement()
	{
		return (referredBy == null ? new ArrayList<Requirement>(0) : new ArrayList<Requirement>(referredBy));
	}

	/**
	 * @return requirement referencing this requirement iterable
	 */
	public List<Requirement> getReferredByRequirementFor(InputRequirementSource source)
	{
		return (referredBy == null ? new ArrayList<Requirement>(0) : referredBy.stream()
				.filter((r) -> r.getSource() == source)
				.distinct()
				.collect(Collectors.toList()));
	}

	/**
	 * @return requirement referencing this requirement iterable
	 */
	public List<InputRequirementSource> getReferredBySource()
	{
		return (referredBy == null ? new ArrayList<InputRequirementSource>(0) : referredBy.stream()
				.filter((r) -> r.getSource() != null)
				.map(Requirement::getSource)
				.distinct()
				.collect(Collectors.toList()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Requirement other)
	{
		int comp = 0;
		if (other == null)
		{
			comp = -1;
		}
		else
		{
			comp = ComparisonChain.start()
					.compare(id, other.id)
					.compare(version, other.version, Ordering.natural().nullsFirst())
					.result();
		}
		return comp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		boolean equals = false;
		if (this == obj)
		{
			equals = true;
		}
		else if (obj instanceof Requirement)
		{
			Requirement other = (Requirement) obj;
			equals = Objects.equals(id, other.id);
			equals = equals && Objects.equals(version, other.version);
		}
		return equals;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return Objects.hash(id, version);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();

		builder.append("Requirement \"");
		builder.append(text);
		builder.append("\"\n\tId: ");
		builder.append(id);
		builder.append("\n\tVersion: ");
		builder.append(version);
		builder.append("\n");
		if (attributes != null)
		{
			for (Map.Entry<String, String> entry : attributes.entrySet())
			{
				builder.append("\t");
				builder.append(entry.getKey());
				builder.append(": ");
				builder.append(entry.getValue());
				builder.append("\n");
			}
		}
		builder.append("\tReferences: ");
		builder.append("\n");
		if (references != null)
		{
			for (Requirement ref : references)
			{
				builder.append("\t\t");
				builder.append(ref.getId());
				builder.append(" version:");
				builder.append(ref.getVersion());
				builder.append("\n");
			}
		}
		builder.append("\tContent: ");
		builder.append(content);

		return builder.toString();
	}
}
