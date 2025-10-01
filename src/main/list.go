package main

import (
  "flag"
  "fmt"
  "time"
)

func main() {
  name := flag.String("name", "", "Imię (wymagane)")
  times := flag.Int("times", 1, "Liczba powtórzeń")
  flag.Parse()

  if *name == "" {
    fmt.Println("Użycie: greet.exe --name IMIĘ [--times 3]")
    return
  }

  now := time.Now().Format("2006-01-02 15:04:05")
  for i := 1; i <= *times; i++ {
    fmt.Printf("[%d] Cześć, %s! Jest %s\n", i, *name, now)
  }
}
