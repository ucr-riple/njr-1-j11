package logic;

import java.util.Set;

import core.Resource;
import core.Triple;

public interface Proposition {

	Set<Triple> toTriples(ResourceIssuer issuer, Resource previous);
	Set<Triple> toTriples();
	Proposition simplify();
	Proposition normalize();

}
