#!/usr/bin/python
# author: Xiheng He

import numpy as np
import matplotlib.pyplot as plt


def sampling(samples):
    current_mean = np.mean(samples)
    current_var = np.var(samples)
    current_sigma = np.sqrt(current_var)
    np.nan_to_num(current_mean)
    np.nan_to_num(current_sigma)
    return current_mean, current_sigma

def posterior(mu, sigma, tau, x):
    np.nan_to_num(mu)
    np.nan_to_num(tau)
    div1 = pow(sigma, 2) * mu + x * pow(tau, 2)
    div2 = pow(sigma, 2) + pow(tau, 2)
    A = div1 / div2
    div3 = pow(sigma, 2) * pow(tau, 2)
    div4 = pow(sigma, 2) + pow(tau, 2)
    B = div3 / div4
    # print(div1, div2, div3, div4)
    B = np.sqrt(B)
    np.nan_to_num(A)
    np.nan_to_num(B)
    return A, np.sqrt(B)


def MAP(filepath, param1, param2):
    mu = param1
    tau = param2
    data = np.loadtxt(filepath, dtype = float, delimiter = '\n')
    res = np.empty([data.size, 2], dtype = float)
    for i in range(len(data)):
        x = data[i]
        samples = data[np.arange(0, i)]
        theta, sigma = sampling(samples)
        if (i > 1):
            A, B = posterior(mu, sigma, tau, x)
            print(A, B)
            res[i][0] = A
            res[i][1] = B
            mu = A
            tau = B
    return res


result = MAP("./U11/uebl12_A2_data.txt", 0, 10)

plot_data = np.empty([1000, 2], dtype = float)
for i in range(len(result)):
    plot_data[i][0] = result[i][0]
    plot_data[i][1] = result[i][1] / (i + 1)
plt.plot(plot_data[:, 1], plot_data[:, 0])
plt.xlabel("sqrt(B) per iteration")
plt.ylabel("A value")
plt.show()
