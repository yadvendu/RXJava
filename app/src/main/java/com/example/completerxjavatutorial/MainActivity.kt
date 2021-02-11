package com.example.completerxjavatutorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.Error
import java.lang.Exception
import java.util.concurrent.TimeUnit
import kotlin.math.sin

class MainActivity : AppCompatActivity() {

    private var compositeDisposable = CompositeDisposable()
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //simpleRxJavObserverObservingValues()
        //createObservableUsingJust()
        //createObservableFromIterable()
        //createObservableUsingCreate()
        //createHotObservableUsingConnectedObservable()
        //createObservableUsingEmpty()
        //createObservableUsingNever()
        //rangeOperator()
        //deferOperator()
        //understandDeferInBetterWay()
        //usingFromCallable()
        //useOfIntervalOperator()
        //creatingSingleObservable()
        //createMaybeObservable()
        //createCompletableObservable()
        //useOfDisposable()
        //useOfMapOperator()
        //useOfFlatMap()
        //useOfFilterOperator()
        //takeOperator()
        //intervalWithTakeOperator()
        //takeWhileOperator()
        //skipOperator()
        //skipWhile()
        //distinctOperator()
        //distinctWithKeySelector()
        //distinctUntilChanged()
        //distinctUntilChangedWithKeySelector()
        //defaultIfEmptyOperator()
        //switchIfEmpty()
        //useOfRepeat()
        //useOfRepeatUntil()
        //sortedOperator()
        //useOfDelayOperator()
        //useOfContainOperator()

        //for error handling in rxjava go through this link
        // -> https://android.jlelse.eu/error-handling-in-rxjava-rxkotlin-e960300990e0
    }

    /**
     * This method prints the number 1,2,3 and 4 using rxJava stream using observable
     */
    private fun simpleRxJavaObservable() {
        Observable.just(1, 2, 3, 4).subscribe {
            Log.d("RxJava ", it.toString())
        }
    }

    /**
     * Below two method :
     * 1.Same as above but here it emits the value with the specified return type
     * 2.Same as above it calls  simpleRxJavObservableEmitValues() and subscribe to it
     * and observe the values.
     */

    /**
     * First method emitting values
     */
    private fun simpleRxJavObservableEmitValues(): Observable<Int> {
        return Observable.just(1, 2, 3, 4)
    }

    /**
     * Second method observing the values
     */
    private fun simpleRxJavObserverObservingValues() {
//        simpleRxJavObservableEmitValues()
//                .subscribe{
//                    Log.d("RxJava ",it.toString())
//                }

        simpleRxJavObservableEmitValues().subscribe {
            Log.d("RxJava ", "Observer 1: $it")
        }

        simpleRxJavObservableEmitValues().subscribe {
            Log.d("RxJava ", "Observer 2: $it")
        }
    }

    /**
     * Create observable using just
     */
    private fun createObservableUsingJust() {
        val observable = Observable.just(1, 2, 3, 4)
        observable.subscribe {
            Log.d("RxJava ", it.toString())
        }
    }

    /**
     * Create observable using iterable
     */
    private fun createObservableFromIterable() {
        val list = listOf<Int>(1, 2, 3, 4)
        val observable = Observable.fromIterable(list)

        observable.subscribe {
            Log.d("RxJava ", it.toString())
        }
    }

    /**
     * Create observable using create
     * Note :
     * We can not send null in rxJava, it will give error
     * To send null we need to use optional as wrapper around rxJava
     */
    private fun createObservableUsingCreate() {
        val observable =
                Observable.create<Int> {
                    it.onNext(1)
                    it.onNext(2)
                    it.onNext(3)
                    it.onNext(4)
                    //it.onNext(null) -> Note : We can never send null in rxJava , it gives error and error block executes
                    it.onComplete()
                }


        observable.subscribe(
                {
                    Log.d("RxJava ", it.toString())
                },
                {
                    Log.d("RxJava ", "Error: Null called onNext")
                },
                {
                    Log.d("RxJava ", "onComplete called")
                }
        )
    }

    /**
     * Creating hot observable using connected observable
     * Note : use publish to convert cold observable to hot
     * Need to call connect to emit value, doesn't emit values only on subscribe
     * It emits only once i.e if two or more subscribers are subscribed then it emits the value
     * to all subscribers at once like 1 at same time to Observer 1 and Observer 2
     * while cold observable emits value to on subscriber() first then again emits values to second subscriber
     *
     * Note: As soon as connect is called observable starts emitting values irrespective of subscriber subscribed to it
     * if any subscriber subscribes to it after calling connect it will miss the emission from hot observable
     */
    private fun createHotObservableUsingConnectedObservable() {
        val connectedHotObservable = Observable.just(1, 2, 3, 4).publish()
        connectedHotObservable.subscribe {
            Log.d("RxJava ", "Observer 1: $it")
        }

        connectedHotObservable.subscribe {
            Log.d("RxJava ", "Observer 2: $it")
        }

        connectedHotObservable.connect()
    }

    /**
     * It emits only onComplete
     *
     * The Empty, Never, and Throw operators generate Observables with very specific and limited behavior. These are useful for testing purposes, and sometimes also for combining with other Observables or as parameters to operators that expect other Observables as parameters.
     */
    private fun createObservableUsingEmpty() {
        val emptyObservable = Observable.empty<Int>()
        emptyObservable.subscribe(
                {
                    Log.d("RxJava ", it.toString())
                },
                {
                    Log.d("RxJava ", "Error: Null called onNext")
                },
                {
                    Log.d("RxJava ", "onComplete called")
                }
        )
    }

    /**
     * It never emits
     *
     * The Empty, Never, and Throw operators generate Observables with very specific and limited behavior. These are useful for testing purposes, and sometimes also for combining with other Observables or as parameters to operators that expect other Observables as parameters.
     */
    private fun createObservableUsingNever() {
        val emptyObservable = Observable.never<Int>()
        emptyObservable.subscribe(
                {
                    Log.d("RxJava ", it.toString())
                },
                {
                    Log.d("RxJava ", "Error: Null called onNext")
                },
                {
                    Log.d("RxJava ", "onComplete called")
                }
        )
    }

    /**
     * It just takes integer and starts printing from the given number and continue to next number
     * till the numberOfTimes emission is allowed
     */
    private fun rangeOperator() {
        val startCountFrom = 5
        val noOfEmissionAllowed = 2
        val observable = Observable.range(startCountFrom, noOfEmissionAllowed)
        observable.subscribe {
            Log.d("RxJava ", it.toString())
        }
    }

    /**
     * Defer takes an observable and its same as range but the difference is when noOfEmission is changed
     * anytime in the code the observer subscribed after changing it, emits as no of times as specified in the
     *  noOfEmissionAllowed.
     *  However, the same is not possible using range means changing noOfEmissionAllowed does not have any eefect
     *  and what specified at the starting is only used.
     *  Basically changing noOfEmissionAllowed creates new observable
     */
    private fun deferOperator() {
        val startCountFrom = 5
        var noOfEmissionAllowed = 3
        val observable = Observable.defer {
            Observable.range(startCountFrom, noOfEmissionAllowed)
        }

        observable.subscribe {
            Log.d("RxJava ", "Observer 1 : $it")
        }

        noOfEmissionAllowed = 2

        observable.subscribe {
            Log.d("RxJava ", "Observer 2 : $it")
        }
    }

    /**
     * In below example changing the car name before subscribing does not have any effect without
     * observableWithoutDefer and whereas changing the car name before subscribing have effect and car name is changed.
     * Note :
     * It means that Defer does not create the Observable until the observer subscribes, and create a fresh Observable for each observer.
     */
    private fun understandDeferInBetterWay() {
        var carName = "BMW"
        val observableWithoutDefer = Observable.just(carName)
        val observableWithDefer = Observable.defer {
            Observable.just(carName)
        }

        carName = "Audi"

        observableWithoutDefer.subscribe {
            Log.d("RxJava ", "Observer without defer : $it")
        }

        observableWithDefer.subscribe {
            Log.d("RxJava ", "Observer with defer : $it")
        }
    }

    /**
     * Note :
     * Both Create and fromCallable defer the execution of the task we specify until an observer subscribes to the ObservableSource.
     * Means, it makes the task "lazy."
     */
    private fun usingFromCallable() {
        //Using just observable is create as soon as this line executes and emits the vale when someone subscribe to it
        //So, it calls the  getNumber() before any method subscribe to it.
        //And if  getNumber() returns some exception app will crash without going into error block of subscriber
        //bcz it called the method even before it was subscribed
        //val observableUsingJust  = Observable.just( getNumber())

        //Using fromCallable it does lazy initialization means when it is subscribed then observable is created,
        //so now if some error occurs in the getNumber() it goes into error block and app does not crash
        //In such situation, it can be very useful
        val observableUsingCallable = Observable.fromCallable {
            getNumber()
        }

//        observableUsingJust.subscribe(
//                {
//                    Log.d("RxJava ", it.toString())
//                },
//                {
//                    Log.d("RxJava Error", it.localizedMessage)
//                }
//        )

        observableUsingCallable.subscribe(
                {
                    Log.d("RxJava ", it.toString())
                },
                {
                    Log.d("RxJava Error", it.localizedMessage)
                }
        )
    }

    private fun getNumber(): Int {
        Log.d("RxJava ", "Calling method called")
        return 1 / 0
    }

    /**
     * Interval Operator create an Observable that emits a sequence of integers spaced by a given time interval. It is used when we want to do a task
     * again and again after some interval.
     * It is used in combination with flat map or map to perform some repeated operation.
     * take operator is used to specify the no of times the value should be emitted or else the value will
     * keep on emitting forever
     * Note :-
     * Operators Timer, Delay, and Interval run on Schedulers.computation(). So we do not need to worry about that.
     * So, no need to subscribe it on background thread but needed to observe on main thread
     * to see the changes.
     */
    private fun useOfIntervalOperator() {
        val observable = Observable.interval(0, 2, TimeUnit.SECONDS)
                .take(5)
                .flatMap {
                    Observable.just("Yadu")
                }

        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Log.d("RxJava ", it)
                }
    }

    /**
     * It emits only one value
     * It has either success or error
     */
    private fun creatingSingleObservable() {
        val singleObservable = Single.just("Hello")
        singleObservable.subscribe(
                {
                    Log.d("RxJava ", it)
                },
                {
                    Log.d("RxJava ", "error")
                }
        )
    }

    /**
     * As the name suggests it may or may not emit the value.
     * If value present emit the value on once in on success
     * and if no value emitted the it may not emit value meaning may or may not call
     * onComplete
     */
    private fun createMaybeObservable() {
        val maybeObservableEmittingValue = Maybe.just("hello")
        maybeObservableEmittingValue.subscribe(
                {
                    Log.d("RxJava ", it)
                },
                {
                    Log.d("RxJava ", "error")
                },
                {
                    Log.d("RxJava ", "completed")
                }
        )

        val maybeObservableEmittingNoValue = Maybe.never<String>()
        maybeObservableEmittingNoValue.subscribe(
                {
                    Log.d("RxJava ", it)
                },
                {
                    Log.d("RxJava ", "error")
                },
                {
                    Log.d("RxJava ", "completed")
                }
        )
    }

    /**
     * Completable does not emit any success value,
     * it has just complete and error block
     * if the task is completed then complete block will be called or
     * in case of error error block will be called
     */
    private fun createCompletableObservable() {
        val completableObservable = Completable.create {
            //Do some task
            it.onComplete()
        }

        completableObservable.subscribe(
                {
                    Log.d("RxJava ", "completed")
                },
                {
                    Log.d("RxJava ", "error")
                }
        )

        //Even trying to emit the value , no value is emitted and only onComplete block is called
        val completeObservableTryingToEmitValues = Completable.fromSingle(Single.just("Hello"))
        completeObservableTryingToEmitValues.subscribe(
                {
                    Log.d("RxJava ", "completed")
                },
                {
                    Log.d("RxJava ", "error")
                }
        )
    }

    /**
     * Disposable disposes the current observable and once disposed nothing will be emitted and hence nothing
     * can be observed
     * Composite disposable is used to add all the disposable and dispose all observables in onDestroy is good
     * practice
     */
    private fun useOfDisposable() {
        val observable = Observable.interval(0, 5, TimeUnit.SECONDS)
                .take(5)
                .flatMap {
                    Observable.just("Yadu")
                }

        val disposable = observable.subscribe {
            Log.d("RxJava ", it)
        }

        compositeDisposable.add(disposable)
    }

    /**
     * Map operator uses any input and convert it into any output type
     * Here it takes integer input and outputs a string
     */
    private fun useOfMapOperator() {
        //Additional Notes :
        //Please look at useOfIntervalOperator where map and flat map is used with the observable
        //So it means map and flat map can be used with both observable and observer
        //In short operator can be used anywhere between creation of observable and subscribing it

        val single = Single.just(1)
        single.map { "Hello World" }
                .subscribe{
            it ->  Log.d("RxJava ", it)
        }
    }

    /**
     * Flat map and map are both very similar with some difference when using it,
     * if after one call again you want to make a call which return a observable just use flat map
     * and if you are just making one call then just use map operator
     * Always remember : Operators can be used on both side i.e. on observable side and on observer side
     * useOfMapOperator() here used on observer side
     * useOfFlatMap() here used on observable side
     * Note : map always wraps the object inside observable and flat map unwraps the object which means have to explicitly
     * wrap the object using observable inside lambda block
     * means : just want to return object from the function after some operation use flat map
     * want to return with observable use map
     *
     * Thumb rule of use of map and flat map :-
     * First Case :-
     * map :- return from called function use map
     * flat map :- return from called function after applying the result from called function to another function(also returning an observable )
     *
     * Second case :
     * 1.Want to return just object from current function after some operation
     * - Use flat map
     * 2.Want to return object inside the observable from current function after some operation
     * - Use map
     */
    private fun useOfFlatMap(){
        getReturnValueAsObservable()
                .subscribe {
                    it -> Log.d("RxJava ", it.toString())
                }
    }

    /**
     * Filter as name suggest just filters are result based on the condition
     * More than one operators can be combined together
     */
    private fun useOfFilterOperator() {
        val observable = Observable.just(1,2,3,4,5)

        observable.filter {
            it % 2 == 0
        }
//                .map {  //use of combination of operators
//            it * 5
//        }
                .subscribe {
            Log.d("RxJava ", it.toString())
        }
    }

    /**
     * It allows the item to be emitted only for the specified time mentioned in the take operator
     */
    private fun takeOperator() {
        val observable = Observable.just(1, 2, 3, 4, 5)

        observable.take(2)
                .subscribe {
                    Log.d("RxJava ", it.toString())
                }
    }

    /**
     * Interval :-  emits after specified time
     * take :- emits only for specified time
     */
    private fun intervalWithTakeOperator() {
       Observable.interval(300,TimeUnit.MILLISECONDS)
                .take(2,TimeUnit.SECONDS)
                .subscribe {
                    Log.d("RxJava ", it.toString())
                }
    }

    /**
     * takeWhile is similar to filter but with a major difference
     * takeWhile -> Stops emitting after conditions fails
     * filter -> Keep on emitting till the end with the specified condition
     * takeWhile emits only emits till it condition is met, once condition fails it stops emitting
     */
    private fun takeWhileOperator() {
        val observable = Observable.just(1,2,3,4,1,2,3,4,5)

        observable.takeWhile {
            it < 3
        }.subscribe {
            Log.d("RxJava ", it.toString())
        }
    }

    /**
     * Skip operator is opposite of take and it skips the number of emission mentioned
     * Here it skips first two emission
     */
    private fun skipOperator() {
        Observable.just(1,2,3,4,5)
                .skip(2)
                .subscribe {
                    Log.d("RxJava ", it.toString())
                }
    }

    /**
     * SkipWhile operator is opposite of takeWhile and it emits everything after condition is not met
     */
    private fun skipWhile() {
        val observable = Observable.just(1,2,3,4,1,2,3,4,5)

        observable.skipWhile {
            it < 3
        }.subscribe {
            Log.d("RxJava ", it.toString())
        }
    }

    /**
     * It avoids repetition or duplication
     */
    private fun distinctOperator() {
        Observable.just(1,2,3,4,1,2,3,4,5)
                .distinct()
                .subscribe {
                    Log.d("RxJava ", it.toString())
                }

    }

    /**
     * Distinct can take some parameters and based on that parameters it can stop repetition
     * here length is the parameter so it avoids to print the second string with same length
     * for ex:- hello and kello both have 5 length
     * it prints the first string because its distinct till now but does not print kello bcz the length is no
     * more distinct now.
     */
    private fun distinctWithKeySelector() {
        Observable.just("foo","fool","cool","hi","hello","kello")
                .distinct{
                    it.length
                }.subscribe {
                    Log.d("RxJava ", it)
                }
    }

    /**
     * It does not emit consecutive duplicates
     */
    private fun distinctUntilChanged() {
        Observable.just(1,2,2,3,4,4,5,1,2,2)
                .distinctUntilChanged()
                .subscribe {
                    Log.d("RxJava ", it.toString())
                }
    }

    /**
     * It does not emit consecutive duplicates
     */
    private fun distinctUntilChangedWithKeySelector() {
        Observable.just("foo","fool","cool","hello","zoo")
                .distinctUntilChanged{
                    it -> it.length
                }.subscribe {
                    Log.d("RxJava ", it)
                }
    }

    /**
     * It prints default value if emitter does not emit any item
     */
    private fun defaultIfEmptyOperator() {
        Observable.just(1,2,3,4,5)
                .filter {
                    it > 10
                }
                .defaultIfEmpty(0)
                .subscribe {
                    Log.d("RxJava ", it.toString())
                }
    }

    /**
     * Same as default but takes observable source as argument and hence
     * we can switch from one observable to another
     * Remember : Use as argument not as lambda in switch statement
     */
    private fun switchIfEmpty() {
        Observable.just(1,2,3,4,5)
                .filter {
                    it > 10
                }
                .switchIfEmpty(Observable.just(10,20,30))
                .subscribe {
                    Log.d("RxJava ", it.toString())
                }
    }

    /**
     * It repeats emission for the specified number of times
     */
    private fun useOfRepeat() {
        Observable.just(1,2,3,4,5)
                .repeat(3)
                .subscribe {
                    Log.d("RxJava ", it.toString())
                }
    }

    /**
     * It repeats till boolean value doesn't become true
     */
    private fun useOfRepeatUntil() {
        Observable.just(1,2,3,4,5)
                .repeatUntil {
                    shouldStopRepeat()
                }
                .subscribe {
                    Log.d("RxJava ", it.toString())
                }
    }

    /**
     * It simply sorts
     */
    private fun sortedOperator() {
        Observable.just(10,2,1,12,5)
                .sorted()
                .subscribe {
                    Log.d("RxJava ", it.toString())
                }
    }

    /**
     * It starts emission after some delay
     */
    private fun useOfDelayOperator() {
        Observable.just(10,2,1,12,5)
                .delay(5000,TimeUnit.MILLISECONDS)
                .subscribe {
                    Log.d("RxJava ", it.toString())
                }
    }

    /**
     * It prints boolean based on whether the value is present or not
     */
    private fun useOfContainOperator() {
        Observable.just(1,2,3,4,5)
                .contains(2)
                .subscribe{ result ->
                    Log.d("RxJava ", result.toString())
                }
    }

    private fun shouldStopRepeat(): Boolean {
        count++
        return count >= 2
    }

    private fun getReturnValueAsObservable() : Single<Int>{
        return firstCall().flatMap {
            val x = it * 5 // modifying the result
            secondCall(x) //making one more call with modified result which again returns a Observable
        }
    }

    private fun secondCall(int: Int): Single<Int> {
        return Single.just(int)
    }

    private fun firstCall(): Single<Int> {
        return Single.just(1)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}




