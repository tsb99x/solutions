from math import floor


def zellers_congruence(year: int, month: int, day: int) -> int:
    q = day
    m = month if month >= 3 else month + 12
    y = year if m < 13 else year - 1  # previous year only for Jan and Feb
    h = (
        q + floor(13 * (m + 1) / 5) + y + floor(y / 4) - floor(y / 100) + floor(y / 400)
    ) % 7  # (0 = Saturday .. 6 = Friday)
    d = ((h + 5) % 7) + 1  # ISO week day (1 = Monday .. 7 = Sunday)
    return d


def test_1st_of_january_year_2000():
    assert zellers_congruence(2000, 1, 1) == 6


def test_1st_of_march_year_2000():
    assert zellers_congruence(2000, 3, 1) == 3


def test_1st_of_january_year_2025():
    assert zellers_congruence(2025, 1, 1) == 3


def test_1st_of_january_year_2026():
    assert zellers_congruence(2026, 1, 1) == 4
