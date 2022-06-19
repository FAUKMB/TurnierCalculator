package test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.Assert.*;

class TableTest{

	@Test
	void test() {
		ArrayList<turnier.Team> l = new ArrayList<>();
		turnier.TableCalculator.clear(l);
		assertEquals("abc", "abc");
	}

}
