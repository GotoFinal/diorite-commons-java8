/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.diorite.commons.enums;

import java.util.List;

enum ValueType
{
    NORMAL,
    EXTENDED,
    DYNAMIC,
    DYNAMIC_EXTENDED;

    static ValueType findType(List<StackTraceElement> frames, Class<?> enumType, Class<?> elementType)
    {
        StackTraceElement stackOne = frames.get(0);
        if (stackOne.getMethodName().equals("<clinit>") && (stackOne.getClassName() == enumType.getName()))
        {
            return ValueType.NORMAL;
        }
        if (frames.size() == 2)
        {
            StackTraceElement stackTwo = frames.get(1);
            if (stackOne.getMethodName().equals("<init>") && (stackOne.getClassName() == elementType.getName()) &&
                stackTwo.getMethodName().equals("<clinit>") &&
                (stackTwo.getClassName() == enumType.getName()))
            {
                return ValueType.EXTENDED;
            }
        }
        if (elementType.isAnonymousClass() && stackOne.getMethodName().equals("<init>") && (stackOne.getClassName() == elementType.getName()))
        {
            return ValueType.DYNAMIC_EXTENDED;
        }
        return ValueType.DYNAMIC;
    }
}
