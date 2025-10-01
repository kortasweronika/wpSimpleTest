#!/usr/bin/perl
use strict;
use warnings;
use Cwd;

print "PERL: starting, cwd=" . (Cwd::getcwd() // '.') . "\n";
print "PERL: args=@ARGV\n";

my $obj;
$obj->do_something();

print "hmmm\n";
