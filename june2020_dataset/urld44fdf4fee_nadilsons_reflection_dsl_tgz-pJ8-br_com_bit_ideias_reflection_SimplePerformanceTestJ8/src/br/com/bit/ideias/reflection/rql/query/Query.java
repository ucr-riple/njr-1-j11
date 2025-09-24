package br.com.bit.ideias.reflection.rql.query;

import java.lang.reflect.Member;
import java.util.List;


/**
 * @author Leonardo Campos
 * @date 01/12/2009
 */
public interface Query {
    public <T extends Member> List<T> list();
    public <T extends Member> T uniqueResult();
}
