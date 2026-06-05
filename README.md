# nEmpires

`nEmpires` is an n-queens inspired puzzle with additional constraints, place emperors on a chess board so that no emperor can attack another.

An emperor moves like a traditional **queen + knight**:

- queen: row, column, and diagonal attacks
- knight: L-shaped horse move attacks

The current greedy implementation places 9 emperors on a 10x10 board:

```text
1000000000
0001000000
0000001000
0000000001
0100000000
0000100000
0000000100
0000000000
0010000000
0000010000
Total of 9 Emperors can be placed on a board with 100 Squares
```
