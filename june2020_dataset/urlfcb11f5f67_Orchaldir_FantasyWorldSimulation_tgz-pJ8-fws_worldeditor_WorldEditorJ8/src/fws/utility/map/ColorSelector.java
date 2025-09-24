package fws.utility.map;

public interface ColorSelector<T extends Cell>
{
	void selectColor(T cell);
}