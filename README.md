# WATER JUG CHALLENGE
This API is a solution to the Water Bucket Riddle problem, 
where the goal is to measure a given amount of liquid using 
two jugs with capacities X and Y, respectively.

To understand it empirically, let's consider the following situation:

You are at the side of a river. You have a 3 liter jug and a
5 liter jug. The jugs do not have markings to allow
measuring smaller quantities. How can you use the jugs to
measure 1 liter of water?

## INITIAL CONDITIONS OF THE PROBLEM
1. Fractions of the liquid cannot be measured.
2. A zero quantity cannot be measured.
3. The jugs have a volume greater than 0.
4. The problem is solved when one of the jugs contains the desired amount.
5. The desired quantity cannot be measured by considering the quantities in both jugs combined.
6. The quantity to be measured is smaller than the jug with the largest volume.
7. There are 3 types of actions: emptying, filling, transferring.

## SOLUTION TO THE PROBLEM
With a bit of algebra, we can discover that this problem is solved using Diophantine equations, which take the following form:
ax + by = z, where
a represents the volume of jug X,
b represents the volume of jug Y, and
z is the amount of liquid to be measured.

From this formulation, we can deduce that our Water Jug problem has a solution if we can solve the Diophantine equation. Using Algebra, we can determine that our problem can only be solved if
z is a multiple of the GCD(a, b).

Understanding this, we can deduce two possible approaches for resolution:

Solution 1: Fill jug X, transfer to jug Y. If Y becomes full, empty Y, transfer again from X to Y. If X is empty, refill X and transfer to Y again, and so on.

Solution 2: Fill jug Y, transfer from Y to X. If X becomes full, empty X, transfer from Y to X. If X is empty, refill X and transfer again to Y, and so on.

The optimal solution to this problem among these two possible solutions will be the one that involves the fewest steps.

The algorithm presented in this development reflects both approaches and searches for the shortest solution.

## Example Payload JSON: POST

Request Body:

```
{
    "bucketX": 10,
    "bucketY": 6,
    "amountWanted": 4
}
```

```
{
    "solution": [
        {
            "step": 1,
            "bucketX": 10,
            "bucketY": 0,
            "action": "Fill bucket X",
            "status": "In progress"
        },
        {
            "step": 2,
            "bucketX": 4,
            "bucketY": 6,
            "action": "Transfer from bucket X to Y",
            "status": "Solved"
        }
    ]
}
```

## Getting Started
This application uses Java 22 along with Spring Boot 3. You can compile it from any IDE (IntelliJ is recommended) using Maven.

## Documentation
Once the application is running, you can find Swagger documentation at:
http://localhost:8080/swagger-ui/index.html