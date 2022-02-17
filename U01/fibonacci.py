#!/usr/bin/python3
from timeit import Timer
import matplotlib.pyplot as plt
import math
import numpy as np

# Python will automatically convert any int that is getting too big into a big int


def divide_and_conquer(num):
    if num == 1 or num == 2:
        return 1
    else:
        return divide_and_conquer(num - 1) + divide_and_conquer(num - 2)


def iteration(num):
    v = l = 1
    a = 0
    for i in range(3, num + 1):
        a = v + l
        v = l
        l = a
    return a


def logarithmic(n):
    f = [0] * (n + 1)
    return helper_log(n, f)


def helper_log(n, f):
    if n == 0:
        return 0
    if n == 1 or n == 2:
        return 1
    k = n // 2
    f[k] = helper_log(k, f)
    if n & 1 == 0:
        if f[k - 1] == 0 and (k - 1) != 0:
            f[k - 1] = helper_log(k - 1, f)
        if f[k + 1] == 0:
            f[k + 1] = helper_log(k + 1, f)
        f[n] = (f[k] * f[k + 1] + f[k] * f[k - 1])
    else:
        if f[k + 1] == 0:
            f[k + 1] = helper_log(k + 1, f)
        f[n] = (f[k] * f[k] + f[k + 1] * f[k + 1])
    return f[n]


def binet(n):
    phi_n = np.uint64(np.power(((1 + np.sqrt(5))/2), n))
    return int(np.floor(np.divide(phi_n, np.sqrt(5)) + 0.5))

"""
inp = int(input("Calculating fib num four different ways, which number? (Hint: Something below 40 or divide and "
                "conquer is going to take forever): "))
print("Divide and Conquer: ", divide_and_conquer(inp))
print("Iterative: ", iteration(inp))
print("Logarithmic: ", logarithmic(inp))
print("Binet Formula: ", binet(inp))
"""


dcTimes = []
itTimes = []
logTimes = []
binTimes = []
fibNumbers = []
stepDC = range(1, 40, 1)
stepsItLogBin = range(1, 1000, 1)

print("Running dc")
for k in stepDC:
    t = Timer(lambda: divide_and_conquer(k))
    dcTimes.append(t.timeit(number=1))

print("Running It")
for i in stepsItLogBin:
    t = Timer(lambda: iteration(i))
    itTimes.append(t.timeit(number=1))
print(itTimes)

print("Running log")
for j in stepsItLogBin:
    t = Timer(lambda: logarithmic(j))
    logTimes.append(t.timeit(number=1))
print(logTimes)

print("Running binet")
for j in stepsItLogBin:
    t = Timer(lambda: binet(j))
    binTimes.append(t.timeit(number=1))
print(binTimes)

print("Running log, logging fib numbers")
for j in stepsItLogBin:
    fibNumbers.append(logarithmic(j))
print(fibNumbers)

fibRatio = []
for r in range(1, len(fibNumbers)-1):
    fibRatio.append(fibNumbers[r]/fibNumbers[r-1])
print(fibRatio)

"""
plt.plot(range(1,23), fibRatio)
plt.xlabel('Inputwert n')
plt.ylabel('Wachstumsrate der Fibonacci Zahlen')
plt.show()
"""

plt.plot(stepsItLogBin, itTimes)
plt.plot(stepsItLogBin, logTimes)
plt.plot(stepsItLogBin, binTimes)
plt.legend(['iterative version', 'recursive version', 'logarithmic version', 'Binet version'], loc='upper left')
plt.xlabel('Fibonacci number')
plt.ylabel('Runtime in sec')
plt.show()


"""
plt.plot(stepDC, dcTimes)
plt.show()
"""