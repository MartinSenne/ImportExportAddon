package com.vaadin.importexport.fp;

import java.util.function.Function;

public abstract class PartialFunction<T, R> implements Function<T, R> {
    public abstract boolean isDefinedAt(T t);

    public R applyOrElse(T t, Function<T, R> defaultFnc) {
        return isDefinedAt(t) ? apply(t) : defaultFnc.apply(t);
    }

    public PartialFunction<T, R> orElse( PartialFunction<T, R> that) {
        return new OrElse<T, R> (this, that);
    }

    private static class OrElse<T, R> extends PartialFunction<T, R> {
        PartialFunction<T, R> f1;
        PartialFunction<T, R> f2;
        
        private OrElse(PartialFunction<T, R> f1, PartialFunction<T, R> f2) {
            this.f1 = f1;
            this.f2 = f2;
        }

        @Override
        public boolean isDefinedAt(T t) {
            return f1.isDefinedAt(t) || f2.isDefinedAt(t);
        }

        @Override
        public R apply(T t) {
            return f1.applyOrElse(t, f2);
        }

        @Override
        public R applyOrElse(T t, Function<T, R> defaultFnc) {
            if (f1.isDefinedAt(t)) {
                return f1.apply(t);
            } else {
                if (f2.isDefinedAt(t)) {
                    return f2.apply(t);
                } else {
                    return defaultFnc.apply(t);
                }
            }
        }

        @Override
        public PartialFunction<T, R> orElse(PartialFunction<T, R> that) {
            return new OrElse<T,R>(f1, f2.orElse(that));
        }
    }
}



//    def applyOrElse[A1 <: A, B1 >: B](x: A1, default: A1 => B1): B1 =
//            if (isDefinedAt(x)) apply(x) else default(x)


//    def orElse[A1 <: A, B1 >: B](that: PartialFunction[A1, B1]): PartialFunction[A1, B1] =
//            new OrElse[A1, B1] (this, that)


//        private class OrElse[-A, +B] (f1: PartialFunction[A, B], f2: PartialFunction[A, B]) extends PartialFunction[A, B] {
//        def isDefinedAt(x: A) = f1.isDefinedAt(x) || f2.isDefinedAt(x)
//
//        def apply(x: A): B = f1.applyOrElse(x, f2)
//
//        override def applyOrElse[A1 <: A, B1 >: B](x: A1, default: A1 => B1): B1 = {
//        val z = f1.applyOrElse(x, checkFallback[B])
//        if (!fallbackOccurred(z)) z else f2.applyOrElse(x, default)
//        }
//
//        override def orElse[A1 <: A, B1 >: B](that: PartialFunction[A1, B1]) =
//        new OrElse[A1, B1] (f1, f2 orElse that)
//
//        override def andThen[C](k: B => C) =
//        new OrElse[A, C] (f1 andThen k, f2 andThen k)
//        }
