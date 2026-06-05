# nEmpires

`nEmpires` is an n-queens inspired puzzle with increased complexity,
place rival emperors on a chess board so that no emperor can attack another.

An emperor moves like a traditional **queen + knight**:

- queen: row, column, and diagonal attacks
- knight: L-shaped horse move attacks

## Result

The original greedy implementation places 9 emperors on a 10x10 board.
The backtracking implementation in `src/nEmpires/nEmpiresBacktracking.java`
found 10:

```text
0010000000
0000010000
0000000010
1000000000
0001000000
0000001000
0000000001
0100000000
0000100000
0000000100
```
