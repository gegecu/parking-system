package model.builders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import model.pojos.ParkingSlot;
import model.util.RandomUtil;

@RunWith(MockitoJUnitRunner.class)
public class ParkingSlotBuilderTest {

	ParkingSlotBuilder parkingSlotBuilder;

	@Mock
	RandomUtil randomUtil;

	@Before
	public void init() {
		parkingSlotBuilder = new ParkingSlotBuilder(randomUtil);
		when(randomUtil.generateRandom(ParkingSlot.Size.values().length)).thenReturn(0);
	}

	@Test
	public void testSlotCreation() {
		// given
		Map<Integer, Integer> distancesPerEntrance = new HashMap<>();
		distancesPerEntrance.put(1, 0);

		// when
		ParkingSlot slot = parkingSlotBuilder.createParkingSlot(0, distancesPerEntrance);

		// then
		assertThat(slot.getSlotId()).isEqualTo(0);
		assertThat(slot.getSize()).isEqualTo(ParkingSlot.Size.SMALL);
	}
}
