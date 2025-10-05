#!/usr/bin/env perl
use strict;
use warnings;
use utf8;            # dla polskich znaków w tekście
use open ':std', ':encoding(UTF-8)';

# Sprawdzenie parametru
my $uuid = shift @ARGV
  or die "Użycie: perl report.pl <UUID>\n";

# (opcjonalnie) prosta walidacja formatu UUID v4/v5 (8-4-4-4-12 heksów)
die "Błędny format UUID: $uuid\n"
  unless $uuid =~ /^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$/;

print "Hello world\n";

my $filename = "id_${uuid}_report.csv";
open my $fh, '>:encoding(UTF-8)', $filename
  or die "Nie mogę utworzyć pliku '$filename': $!";

# Dokładnie taki wiersz, z podstawionym UUID
print {$fh} "id_${uuid}|Parametry przekazane poprawnie|2025-10-01 17:06:42|2025-10-01 17:06:42|FAIL\n";
close $fh or die "Błąd zapisu do pliku '$filename': $!";

print "Utworzono plik: $filename\n";
