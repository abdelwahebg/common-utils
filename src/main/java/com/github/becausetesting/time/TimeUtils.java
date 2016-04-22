package com.github.becausetesting.time;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.MonthDay;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.joda.time.LocalDate;
import org.joda.time.Minutes;

public class TimeUtils {

	/*
	 * LocalDateTime ==Year +Month /YearMonth MonthDay DayOfWeek
	 */
	public static enum UNIT {
		YEAR, MONTH, WEEK, DAYS, HOURS, MINUTES, SECONDS, NANOSECONDS;
	}

	public LocalDateTime getCurrentTime() {
		return LocalDateTime.now();
	}

	public LocalDateTime ofDateTime(int year, int month, int day) {
		return LocalDateTime.of(year, month, day, 0, 0);
	}
	
	public LocalDateTime ofDateTime(Instant instant){
		LocalDateTime ofInstant = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
		return ofInstant;
	}

	public String dateTimeString(LocalDateTime date, String pattern) {
		return date.format(DateTimeFormatter.ofPattern(pattern));
	}

	public LocalDateTime string2DateTime(String datetime, String pattern) {
		return LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern(pattern));
	}
	
	public long getSeconds(LocalDateTime dateTime){
		//long epochSecond = dateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
		long epochSecond = dateTime.atZone(ZoneOffset.UTC).toEpochSecond();
		return epochSecond;
	}
	public Instant toInstant(LocalDateTime dateTime){
		Instant instant = dateTime.toInstant(ZoneOffset.UTC);
		return instant;
	}

	/**
	 * whichdayOfWeek:
	 * TODO
	 *
	 * @author Administrator
	 * @param datetime
	 * @return
	 * @since JDK 1.8
	 */
	public String whichdayOfWeek(LocalDateTime datetime) {
		String displayName = datetime.getDayOfWeek().getDisplayName(TextStyle.FULL_STANDALONE, Locale.CHINA);
		return displayName;
	}

	/**
	 * dateAdd:
	 *
	 * @author Administrator
	 * @param unit
	 * @param amount
	 * @param dateTime
	 * @return
	 * @since JDK 1.8
	 */
	public LocalDateTime dateAdd(UNIT unit, long amount, LocalDateTime dateTime) {
		LocalDateTime localDateTime = LocalDateTime.now();
		switch (unit) {
		case YEAR:
			localDateTime = dateTime.plus(amount, ChronoUnit.YEARS);
			break;
		case MONTH:
			localDateTime = dateTime.plus(amount, ChronoUnit.MONTHS);
			break;
		case DAYS:
			localDateTime = dateTime.plus(amount, ChronoUnit.DAYS);
			break;
		case HOURS:
			localDateTime = dateTime.plus(amount, ChronoUnit.HOURS);
			break;
		case MINUTES:
			localDateTime = dateTime.plus(amount, ChronoUnit.MINUTES);
			break;
		case SECONDS:
			localDateTime = dateTime.plus(amount, ChronoUnit.SECONDS);
			break;
		case NANOSECONDS:
			localDateTime = dateTime.plus(amount, ChronoUnit.NANOS);
			break;
		case WEEK:
			localDateTime = dateTime.plus(amount, ChronoUnit.WEEKS);
			break;
		default:
			break;
		}

		return localDateTime;
	}

	public long dateDiff(UNIT unit, LocalDateTime start, LocalDateTime end) {
		long amount = 0l;
		//Duration between = Duration.between(start, end);
		switch (unit) {
		case YEAR:
			amount=ChronoUnit.YEARS.between(start, end);
			break;
		case MONTH:
			amount=ChronoUnit.MONTHS.between(start, end);
			break;
		case WEEK:
			amount=ChronoUnit.WEEKS.between(start, end);
			break;
		case DAYS:
			amount = ChronoUnit.DAYS.between(start, end);
			break;
		case HOURS:
			amount = ChronoUnit.HOURS.between(start, end);
			break;
		case MINUTES:
			amount = ChronoUnit.MINUTES.between(start, end);
			
			//start.until(end, ChronoUnit.MINUTES);
			//TimeUnit.MINUTES.toHours(1);
			break;
		case SECONDS:
			amount = ChronoUnit.SECONDS.between(start, end);
			break;
		case NANOSECONDS:
			amount = ChronoUnit.NANOS.between(start, end);
			break;
		default:
			break;
		}
		return amount;
	}

}
