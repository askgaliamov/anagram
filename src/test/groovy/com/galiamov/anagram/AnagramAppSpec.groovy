package com.galiamov.anagram

import spock.lang.Specification

class AnagramAppSpec extends Specification {

  def out = new ByteArrayOutputStream()

  def setup() {
    System.setOut(new PrintStream(out))
  }

  def cleanup() {
    System.setOut(null)
  }

  def "find anagrams"() {
    when:
    AnagramApp.main(["src/test/resources/sample00.txt"] as String[])

    then:
    out.toString() == "act cat\nacre care race\n"
  }

  def "verify stability"() {
    when:
    AnagramApp.main(["src/test/resources/sample01.txt"] as String[])

    then:
    out.toString() == "act cat\nacre care race\n"
  }

}
