KalmanFilterAccelerometer
=========================

Testing Kalman Filter for accelerometer data

Dependiences
------------

- Guava

Input:
------

```bash
acc_log.dat file
```

with format:

    timestamp [millis] | accelerometer (one of axis)
    
    65464208 0.25911117
    65464260 0.20279828149999996
    65464281 0.2315574732583333
    65464293 0.1434090791808333
    65464309 0.14202263688562494
    .
    .
    .

Adjusting accuracy:
----------

Adjust:

```bash
Constants.FILTER_GAIN
```

to value in range [0.0 - 1.0].
Smaller the value is -> Kalman filter algorithm has less impact to the final data. 

Output:
-------

```bash
new_acc_log.dat
```

Usage with [gnuplot]:
---------------------

```bash
plot "acc_log.dat" using 1:2 w l, "new_acc_log.dat" using 1:2 w l
```

ALL (raw data + two Kalman charts):

![alt tag](https://raw.githubusercontent.com/Bresiu/KalmanFilterAccelerometer/master/charts/all.png)

RAW DATA:

![alt tag](https://raw.githubusercontent.com/Bresiu/KalmanFilterAccelerometer/master/charts/raw.png)

KALMAN FILTER WITH FILTER_GAIN == 0.9:

![alt tag](https://raw.githubusercontent.com/Bresiu/KalmanFilterAccelerometer/master/charts/gain0_9.png)

KALMAN FILTER WITH FILTER_GAIN == 0.95:

![alt tag](https://raw.githubusercontent.com/Bresiu/KalmanFilterAccelerometer/master/charts/gain0_95.png)

[gnuplot]:http://www.gnuplot.info/
