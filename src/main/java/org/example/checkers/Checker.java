package org.example.checkers;

import org.example.FlatLoginHistory;

public interface Checker {
    FlatLoginHistory apply(FlatLoginHistory line);
}
