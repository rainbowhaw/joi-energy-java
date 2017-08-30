package test.uk.tw.energy.service;

import org.assertj.core.api.BigDecimalAssert;
import org.junit.Test;
import uk.tw.energy.domain.ElectricityReading;
import uk.tw.energy.domain.MeterData;
import uk.tw.energy.domain.Tariff;
import uk.tw.energy.service.CostService;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CostServiceTest {

    private CostService costService = new CostService();

    @Test
    public void getCostReturnsCostForMeterReadingOnTariff() {
        BigDecimal unitRate = BigDecimal.valueOf(0.20);
        Tariff tariff = new Tariff("Elmo's Excellent Electricity", unitRate);

        Instant beginningOfDay = Instant.ofEpochMilli(1504134000);
        Instant beginningOfFollowingDay = Instant.ofEpochMilli(1504137600);
        ElectricityReading firstReading = new ElectricityReading(beginningOfDay, BigDecimal.valueOf(1000.000));
        ElectricityReading lastReading = new ElectricityReading(beginningOfFollowingDay, BigDecimal.valueOf(1012.000));
        List<ElectricityReading> electricityReadings = new ArrayList<>();
        electricityReadings.add(firstReading);
        electricityReadings.add(lastReading);
        MeterData meterData = new MeterData(electricityReadings);

        BigDecimal cost = costService.calculateCost(meterData, tariff);

        assertThat(cost).isEqualTo(new BigDecimal("2.40"));
    }
}
